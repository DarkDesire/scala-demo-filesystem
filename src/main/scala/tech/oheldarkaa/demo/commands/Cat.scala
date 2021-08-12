package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.filesystem.State

class Cat(fileName: String) extends Command {
  def apply(state: State): State = {
    val wd = state.wd
    val dirEntry = wd.findEntry(fileName)
    if (dirEntry == null || !dirEntry.isFile)
      state.setMessage(fileName + ": no such file")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}
