package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

class Echo(args: List[String]) extends Command {
  def apply(state: State): State = ???
}
