package repositories

sealed trait RepoError extends Throwable

object RepoError {

  final case class ReadError(err: Throwable) extends RepoError {
    println(" There Was a Read Error ", err)
  }

  final case class WriteError(err: Throwable) extends RepoError {
    println(" There Was a Write Error ", err)
  }

}

