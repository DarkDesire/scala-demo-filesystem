package tech.oheldarkaa.demo.filesystem

import tech.oheldarkaa.demo.files.Directory

object State {
  val SHELL_TOKEN = "[FS] "

  def apply(root: Directory, wd: Directory, output: String = ""): State =
    new State(root, wd, output)
}

class State(val root: Directory, val wd: Directory, val output: String) {

  def show(): Unit = {
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State =
    State(root,wd,message)
}

