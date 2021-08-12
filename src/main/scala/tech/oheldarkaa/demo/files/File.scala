package tech.oheldarkaa.demo.files

import tech.oheldarkaa.demo.filesystem.FilesystemException

object File {
  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")

}

class File(override val parentPath: String,
           override val name: String,
           val contents: String)
  extends DirEntry(parentPath, name) {

  def asFile: File = this

  def asDirectory: Directory =
    throw new FilesystemException("A file cannot be converted to Directory")

  def getType: String = "File"

  def isDirectory: Boolean = false
  def isFile: Boolean = true

  def setContents(newContents: String): File = new File(parentPath, name, newContents)
  def appendContents(newContents: String): File = setContents(contents + "\n" + newContents)
}
