/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses command line arguments.<br/><br/>
 * Created: 21.10.2021 14:54:04
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class CommandLineParser {

  private final List<CommandLineItem> items;
  private final List<CommandLineArgument> arguments;
  private int requiredArgumentCount;

  // parser construction ---------------------------------------------------------------------------------------------

  public CommandLineParser() {
    this.items = new ArrayList<>();
    this.arguments = new ArrayList<>();
    this.requiredArgumentCount = 0;
  }

  public void addFlag(String property, String longName, String shortName) {
    items.add(new CommandLineFlag(property, longName, shortName));
  }

  public void addOption(String property, String longName, String shortName) {
    items.add(new CommandLineOption(property, longName, shortName));
  }

  public void addArgument(String property, boolean required) {
    arguments.add(new CommandLineArgument(property, required));
    if (required) {
      requiredArgumentCount++;
    }
  }

  // parsing ---------------------------------------------------------------------------------------------------------

  public <T extends CommandLineConfig> T parse(T config, String... args) {
    int consumedArgs = 0;
    int i = 0;
    while (i < args.length) {
      String arg = args[i];
      if (isHelp(arg)) {
        config.setHelp(true);
        return config;
      }
      if (isVersion(arg)) {
        config.setVersion(true);
        return config;
      }
      CommandLineItem item = findItem(arg);
      if (item instanceof CommandLineFlag) {
        CommandLineFlag flag = (CommandLineFlag) item;
        BeanUtil.setPropertyValue(config, flag.getProperty(), true, true, true);
      } else if (item instanceof CommandLineOption) {
        parseOption((CommandLineOption) item, args[i], i, args, config);
        i++;
      } else {
        if (arg.startsWith("-")) {
          throw ExceptionFactory.getInstance().illegalCommandLineOption(arg);
        }
        if (consumedArgs < arguments.size()) {
          CommandLineArgument argument = arguments.get(consumedArgs++);
          BeanUtil.setPropertyValue(config, argument.getProperty(), arg, true, true);
        } else {
          throw ExceptionFactory.getInstance().illegalCommandLineArgument("Illegal command line argument: " + arg);
        }
      }
      i++;
    }
    if (consumedArgs < requiredArgumentCount) {
      throw ExceptionFactory.getInstance().missingCommandLineArgument();
    }
    return config;
  }

  // formatting ------------------------------------------------------------------------------------------------------

  public static String formatArgs(String[] args) {
    StringBuilder result = new StringBuilder();
    for (String arg : args) {
      if (result.length() > 0) {
        result.append(' ');
      }
      result.append(StringUtil.quoteIfContainsSpecialChars(arg, '"', " "));
    }
    return result.toString();
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private <T extends CommandLineConfig> void parseOption(CommandLineOption option, String usedName, int i, String[] args, T config) {
    if (i < args.length - 1) {
      String optionValue = args[++i];
      try {
        BeanUtil.setPropertyValue(config, option.getProperty(), optionValue, true, true);
      } catch (Exception e) {
        throw ExceptionFactory.getInstance().illegalCommandLineOptionValue(usedName, optionValue);
      }
    } else {
      throw ExceptionFactory.getInstance().missingCommandLineOptionValue(usedName);
    }
  }

  private boolean isHelp(String arg) {
    return ("--help".equals(arg) || "-h".equals(arg) || "-help".equals(arg));
  }

  private boolean isVersion(String arg) {
    return ("--version".equals(arg) || "-v".equals(arg) || "-version".equals(arg));
  }

  private CommandLineItem findItem(String name) {
    for (CommandLineItem item : items) {
      if (name.equals(item.getLongName()) || name.equals(item.getShortName())) {
        return item;
      }
    }
    return null;
  }

}
