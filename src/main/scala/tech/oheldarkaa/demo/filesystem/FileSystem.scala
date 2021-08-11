package tech.oheldarkaa.demo.filesystem

import tech.oheldarkaa.demo.commands.Command
import tech.oheldarkaa.demo.files.Directory
import tech.oheldarkaa.demo.filesystem.State.SHELL_TOKEN

import java.util.Scanner

object FileSystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while (true) {
    state.show()
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
