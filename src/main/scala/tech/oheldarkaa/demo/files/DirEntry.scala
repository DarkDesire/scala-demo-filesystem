package tech.oheldarkaa.demo.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separatorIfNecessary =
      if (Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR

    parentPath + separatorIfNecessary + name
  }

  def asDirectory: Directory

  def isDirectory: Boolean

  def asFile: File

  def isFile: Boolean

  def getType: String

}
