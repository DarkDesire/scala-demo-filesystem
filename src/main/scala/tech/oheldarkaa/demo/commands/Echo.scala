package tech.oheldarkaa.demo.commands

import tech.oheldarkaa.demo.files.{Directory, File}
import tech.oheldarkaa.demo.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {

  // topIndex - NON-INCLUSIVE!
  private def createContent(args: Array[String], topIndex: Int): String = {
    @tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String = {
      val spaceIfNecessary = if (currentIndex>0) " " else ""
      if (currentIndex>=topIndex) accumulator
      else createContentHelper(currentIndex+1, accumulator + spaceIfNecessary + args(currentIndex))
    }
    createContentHelper(currentIndex = 0, accumulator = "")
  }

  def doEcho(state: State,
             contents: String,
             fileName: String,
             append: Boolean): State = {
    def getRootAfterEcho(currentDirectory: Directory,
                         path: List[String],
                         contents: String,
                         append: Boolean): Directory = {
      /*
        if path is empty => fail => currentDirectory
        else if no more things to explore => path.tail.isEmpty
          find the file to create/add content to
          if file not found, then create file
          else if the entry is actually a directory, then fail
          else
            replace or append content to the file
            replace the entry with the filename with the NEW file
         else
          find the next directory to navigate
          call gRAE recursively on that

          if recursive call failed, fail
          else replace entry with the NEW directory after the recursive call
       */
      if (path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) {
        val dirEntry = currentDirectory.findEntry(path.head)

        if (dirEntry == null)
          currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
        else if (dirEntry.isDirectory) currentDirectory
        else
          if (append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
          else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
      }
      else {
        val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
        val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)

        if (newNextDirectory == nextDirectory) currentDirectory
        else currentDirectory.replaceEntry(path.head, newNextDirectory)

      }
    }

    // echo something > pwd/someFile
    if (fileName.contains(Directory.SEPARATOR))
      state.setMessage("Echo: filename must not contain separators!")
    else {
      val newRoot: Directory =
        getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ fileName, contents, append)
      if (newRoot == state.root)
        state.setMessage(fileName + ": no such file")
      else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  def apply(state: State): State = {
    if (args.isEmpty) state
    else if (args.length == 1) state.setMessage(args(0))
    else {
      val operator = args(args.length-2) // next to last
      val fileName = args(args.length-1)
      val contents = createContent(args, args.length-2)

      if (">>".equals(operator))
        doEcho(state, contents, fileName, append = true)
      else if (">".equals(operator))
        doEcho(state, contents, fileName, append = false)
      else
        state.setMessage(createContent(args, args.length))
    }
  }
}
