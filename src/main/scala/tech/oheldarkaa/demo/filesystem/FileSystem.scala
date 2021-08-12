package tech.oheldarkaa.demo.filesystem

import tech.oheldarkaa.demo.commands.Command
import tech.oheldarkaa.demo.files.Directory

object FileSystem extends App {
  val root = Directory.ROOT
  val initialState = State(root, root)
  initialState.show

  io.Source.stdin.getLines()
    .foldLeft(initialState)((currentState, newLine) => {
      val newState = Command.from(newLine).apply(currentState)
      newState.show()
      newState
    })
}
