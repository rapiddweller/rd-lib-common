/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.exception;

/**
 * Lists exit popular exit codes of processes.<br/><br/>
 * Created: 17.11.2021 14:46:04
 * @author Volker Bergmann
 * @since 1.1.4
 */
public final class ExitCodes {

  /** Successful termination. */
  public static final int OK = 0;

  /** Miscellaneous error. */
  public static final int MISCELLANEOUS_ERROR = 1;

  /** Syntax error. */
  public static final int SYNTAX_ERROR = 2;

  /** Permission error. */
  public static final int PERMISSION_ERROR = 3;

  /** Command line usage error.
   *  The command was used incorrectly, e.g., with the wrong number of arguments,
   *  a bad flag, a bad syntax in a parameter, or whatever. */
  public static final int COMMAND_LINE_USAGE_ERROR = 64;

  /** Data format error. The input data was incorrect in some way.
   *  This should only be used for user's data & not system files. */
  public static final int DATA_FORMAT_ERROR = 65;

  /** Cannot open input. An input file (not a system file) did not exist or was not readable. */
  public static final int FILE_NOT_FOUND = 66;

  /** Addressee unknown. The user specified did not exist.
   *  This might be used for mail addresses or remote logins. */
  public static final int USER_UNKNOWN = 67;

  /** Host name unknown. The host specified did not exist. This is used in mail addresses or network requests. */
  public static final int HOST_NAME_UNKNOWN = 68;

  /** Service unavailable. This can occur if a support program or file does not exist.
   *  This can also be used as a catchall message when something you wanted to do doesn't work,
   *  but you don't know why. */
  public static final int SERVICE_UNAVAILABLE = 69;

  /** Internal software error.
   *  This should be limited to non-operating system related errors as possible. */
  public static final int INTERNAL_SOFTWARE_ERROR = 70;

  /** Operating system error. This is intended to be used for such things as "cannot fork",
   *  "cannot create pipe", or the like. It includes things like getuid returning a user that does not
   *  exist in the passwd file. */
  public static final int OS_ERROR = 71;

  /** Critical OS file missing. Some system file  = e.g., /etc/passwd, /etc/utmp, etc.) does not exist,
   *  cannot be opened, or has some sort of error  = e.g., syntax error). */
  public static final int OS_FILE_MISSING = 72;

  /** Cannot create  = user specified) output file. */
  public static final int CANT_CREATE_FILE = 73;

  /** Input/output error on some file. */
  public static final int I_O_ERROR = 74;

  /** Temporary failure, indicating something that is not really an error.
   *  In sendmail, this means that a mailer  = e.g.) could not create a connection,
   *  and the request should be reattempted later. */
  public static final int TEMPORARY_FAIL = 75;

  /** Remote error in protocol.
   *  The remote system returned something that was "not possible" during a protocol exchange. */
  public static final int PROTOCOL_ERROR = 76;

  /** Permission denied. You did not have sufficient permission to perform the operation. This is not intended for
   *  file system problems, which should use NOINPUT or CANTCREAT, but rather for higher level permissions. */
  public static final int PERMISSION_DENIED = 77;

  /** configuration error. */
  public static final int CONFIGURATION_ERROR = 78;

  /** Permission problem or command is not an executable. */
  public static final int CANNOT_EXECUTE = 126;

  /** Command not found. Possible problem with $PATH or a typo. */
  public static final int COMMAND_NOT_FOUND = 127;

  /** Invalid argument to exit. */
  public static final int INVALID_ARGUMENT_TO_EXIT = 128;

  /** Terminated by Control-C. */
  public static final int TERMINATED_BY_CONTROL_C = 130;

  private ExitCodes() {
    // private constructor to prevent instantiation of this constants provider class
  }

}
