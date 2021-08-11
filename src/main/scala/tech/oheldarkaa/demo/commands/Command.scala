package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

object Command {
  def from(input: String): Command = new UnknownCommand
}
trait Command {
  def apply(state: State): State
}
