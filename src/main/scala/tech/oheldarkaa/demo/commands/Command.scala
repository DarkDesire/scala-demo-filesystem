package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

object Command {
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"

  def noCommand: Command =
    (state: State) => state.setMessage("Type any command.")
  def emptyCommand: Command =
    (state: State) => state.setMessage("")
  def incompleteCommand(name:String): Command =
    (state: State) => state.setMessage(name + ": Incomplete command!")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split("\\W+").filterNot(_ == "")

    println(s"tokens:${tokens.zipWithIndex.mkString}")
    if (tokens.isEmpty) noCommand
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) {
        incompleteCommand(MKDIR)
      }
      else new Mkdir(tokens(1))
    }
    else if (LS.equals(tokens(0))) new Ls
    else if (PWD.equals(tokens(0))) new Pwd
    else new UnknownCommand
  }
}
trait Command {
  def apply(state: State): State
}
