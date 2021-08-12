package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

class UnknownCommand(name: String) extends Command {
  override def apply(state: State): State = {
    state.setMessage(name+": Command not found!")
  }
}
