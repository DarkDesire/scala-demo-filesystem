package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.files.DirEntry
import tech.oheldarkaa.demo.filesystem.State

class Ls extends Command {
  private def createNiceOutput(contents: List[DirEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]\n" + createNiceOutput(contents.tail)
    }
  }
  def apply(state: State): State = {
    val contents = state.wd.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }
}
