package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.files.{DirEntry, File}
import tech.oheldarkaa.demo.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  def createSpecificEntry(state: State): DirEntry = {
    File.empty(state.wd.path, name)
  }
}