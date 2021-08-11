package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State = {
    state.setMessage("Command not found!")
  }
}
