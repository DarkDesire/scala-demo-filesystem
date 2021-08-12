package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.files.{DirEntry, Directory}
import tech.oheldarkaa.demo.filesystem.State

class Mkdir(name: String) extends Command {

  private def doMkdir(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory,
                        path: List[String],
                        newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }
    val wd = state.wd

    // 1.all the directories in the fullPath
    val allDirsInPath = wd.getAllFoldersInPath

    // 2.create new directory entry in the wd
    val newDir = Directory.empty(wd.path, name)

    // 3.update directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newDir)

    // 4.find new WD INSTANCE given wd's full path, in the NEW directory structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }
  private def checkIllegal(name: String): Boolean = name.contains(".")
  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists")
    }
    else if (name.contains(Directory.SEPARATOR)) {
      // mkdir -p something/something2 forbidden
      state.setMessage(name + " must not contain separators!")
    }
    else if (checkIllegal(name)) {
      state.setMessage(name + ": illegal entry name!")
    } else {
      doMkdir(state, name)
    }
  }
}