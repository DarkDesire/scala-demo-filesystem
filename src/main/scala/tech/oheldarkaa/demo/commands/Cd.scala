package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.files.{DirEntry, Directory}
import tech.oheldarkaa.demo.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {
  def apply(state: State): State = {
    /*
     cd /something/something2/../
     cd a/b/c = relative to current working dir
     */

    // 1. find root
    val root = state.root
    val wd = state.wd

    // 2. find the abs path of the dir I want to CD to
    val absolutePath = {
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir
    }
    // 3. find the directory to CD to, given the path
    val destinationDirectory = doFindEntry(root, absolutePath)
    // 4. change the state, give the new dir
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + ": no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }

  private def doFindEntry(root: Directory, path: String): DirEntry = {

    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }
    // 1. tokens
    val tokens: List[String] = path.substring(1)
      .split(Directory.SEPARATOR).toList

    // 2. navigate to the correct entry
    findEntryHelper(root, tokens)
  }

}
