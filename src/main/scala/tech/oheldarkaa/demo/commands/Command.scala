package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

object Command {
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"
  val CAT = "cat"

  def noCommand: Command =
    (state: State) => state.setMessage("Type any command.")

  def emptyCommand: Command =
    (state: State) => state.setMessage("")

  def incompleteCommand(name: String): Command =
    (state: State) => state.setMessage(name + ": Incomplete command!")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ").filter(_.nonEmpty)

    println(s"tokens:${tokens.zipWithIndex.map(_.swap).mkString}")
    if (tokens.isEmpty) noCommand
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    }
    else if (LS.equals(tokens(0))) new Ls
    else if (PWD.equals(tokens(0))) new Pwd
    else if (TOUCH.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Touch(tokens(1))
    }
    else if (CD.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CD)
      else new Cd(tokens(1))
    }
    else if (RM.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(RM)
      else new Rm(tokens(1))
    }
    else if (ECHO.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(ECHO)
      else new Echo(tokens.tail)
    }
    else if (CAT.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CAT)
      else new Cat(tokens(1))
    }
    else new UnknownCommand
  }
}

trait Command {
  def apply(state: State): State
}
