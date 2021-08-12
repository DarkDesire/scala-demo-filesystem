package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.files.{DirEntry, Directory}
import tech.oheldarkaa.demo.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  def createSpecificEntry(state: State): DirEntry = {
    Directory.empty(state.wd.path, name)
  }
}
