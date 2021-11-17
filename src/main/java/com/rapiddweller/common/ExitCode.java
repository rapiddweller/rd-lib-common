/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

/**
 * Lists exit popular exit codes of processes.<br/><br/>
 * Created: 17.11.2021 14:46:04
 * @author Volker Bergmann
 * @since 1.1.4
 */
public enum ExitCode {

  /** Successful termination. */
  OK(0),

  /** Miscellaneous error. */
  MISCELLANEOUS_ERROR(1),

  /** Syntax error. */
  SYSTEM_ERROR(2),

  /** Permission error. */
  PERMISSION_ERROR(3),

  /** Command line usage error.
   *  The command was used incorrectly, e.g., with the wrong number of arguments,
   *  a bad flag, a bad syntax in a parameter, or whatever. */
  COMMAND_LINE_USAGE_ERROR(64),

  /** Data format error. The input data was incorrect in some way.
   *  This should only be used for user's data & not system files. */
  DATA_FORMAT_ERROR(65),

  /** Cannot open input. An input file (not a system file) did not exist or was not readable.
   *  This could also include errors like "No message" to a mailer
   *  (if it cared to catch it). */
  NO_INPUT(66),

  /** Addressee unknown. The user specified did not exist.
   *  This might be used for mail addresses or remote logins. */
  USER_UNKNOWN(67),

  /** Host name unknown. The host specified did not exist. This is used in mail addresses or network requests. */
  HOST_NAME_UNKNOWN(68),

  /** Service unavailable. This can occur if a support program or file does not exist.
   *  This can also be used as a catchall message when something you wanted to do doesn't work,
   *  but you don't know why. */
  SERVICE_UNAVAILABLE(69),

  /** Internal software error.
   *  This should be limited to non-operating system related errors as possible. */
  INTERNAL_SOFTWARE_ERROR(70),

  /** Operating system error. This is intended to be used for such things as "cannot fork",
   *  "cannot create pipe", or the like. It includes things like getuid returning a user that does not
   *  exist in the passwd file. */
  OS_ERROR(71),

  /** Critical OS file missing. Some system file (e.g., /etc/passwd, /etc/utmp, etc.) does not exist,
   *  cannot be opened, or has some sort of error (e.g., syntax error). */
  OS_FILE_MISSING(72),

  /** Cannot create (user specified) output file. */
  CANT_CREATE_FILE(73),

  /** Input/output error on some file. */
  I_O_ERROR(74),

  /** Temporary failure, indicating something that is not really an error.
   *  In sendmail, this means that a mailer (e.g.) could not create a connection,
   *  and the request should be reattempted later. */
  TEMPORARY_FAIL(75),

  /** Remote error in protocol.
   *  The remote system returned something that was "not possible" during a protocol exchange. */
  PROTOCOL_ERROR(76),

  /** Permission denied. You did not have sufficient permission to perform the operation. This is not intended for
   *  file system problems, which should use NOINPUT or CANTCREAT, but rather for higher level permissions. */
  PERMISSION_DENIED(77),

  /** configuration error. */
  CONFIGURATION_ERROR(78),

  /** Permission problem or command is not an executable. */
  CANNOT_EXECUTE(126),

  /** Command not found. Possible problem with $PATH or a typo. */
  COMMAND_NOT_FOUND(127),

  /** Invalid argument to exit. */
  INVALID_ARGUMENT_TO_EXIT(128),

  /** Terminated by Control-C. */
  TERMINATED_BY_CONTROL_C(130);

  private final int code;

  ExitCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
