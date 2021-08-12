package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

class Cat(name: String) extends Command {
  def apply(state: State): State = ???
}
