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

    //println(s"tokens:${tokens.zipWithIndex.map(_.swap).mkString}")
    if (input.isEmpty | tokens.isEmpty) noCommand
    else tokens(0) match {
      case MKDIR =>
        if (tokens.length < 2) incompleteCommand(MKDIR)
        else new Mkdir(tokens(1))
      case LS => new Ls
      case PWD => new Pwd
      case TOUCH =>
        if (tokens.length < 2) incompleteCommand(MKDIR)
        else new Touch(tokens(1))
      case CD =>
        if (tokens.length < 2) incompleteCommand(CD)
        else new Cd(tokens(1))
      case RM =>
        if (tokens.length < 2) incompleteCommand(RM)
        else new Rm(tokens(1))
      case ECHO =>
        if (tokens.length < 2) incompleteCommand(ECHO)
        else new Echo(tokens.tail)
      case CAT =>
        if (tokens.length < 2) incompleteCommand(CAT)
        else new Cat(tokens(1))
      case _ => new UnknownCommand(tokens(0))
    }
  }
}

trait Command extends (State => State)
