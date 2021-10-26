/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

import com.rapiddweller.common.collection.MapEntry;
import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.file.FileByNameFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Provides stream operations.
 * Created: 17.07.2006 22:17:42
 * @author Volker Bergmann
 * @since 0.1
 */
public final class IOUtil {

  private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

  private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; de-DE; rv:1.7.5) Gecko/20041122 Firefox/1.0";

  // convenience unchecked-exception operations ----------------------------------------------------------------------

  /** Convenience method that closes a {@link Closeable} if it is not null
   *  and logs possible exceptions without disturbing program execution.
   *  @param closeable the resource to close */
  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        logger.error("Error closing " + closeable, e);
      }
    }
  }

  /** Closes all elements. If one or more exceptions occur,
   *  the closing process is first completed, then an exception
   *  is raised which reports the first exception to the caller. */
  @SafeVarargs
  public static <T extends Closeable> void closeAll(T... closeables) {
    if (closeables != null) {
      Throwable t = null;
      for (Closeable closeable : closeables) {
        try {
          closeable.close();
        } catch (IOException e) {
          logger.error("Error closing " + closeable, e);
        } catch (Throwable e) {
          t = e;
        }
      }
      if (t != null) {
        throw new RuntimeException("Error closing resources", t);
      }
    }
  }

  /** Closes all elements. If one or more exceptions occur,
   *  the closing process is first completed, then an exception
   *  is raised which reports the first exception to the caller. */
  public static <T extends Collection<? extends Closeable>> void closeAll(T closeables) {
    if (closeables != null) {
      Throwable t = null;
      for (Closeable closeable : closeables) {
        try {
          closeable.close();
        } catch (IOException e) {
          logger.error("Error closing " + closeable, e);
        } catch (Throwable e) {
          t = e;
        }
      }
      if (t != null) {
        throw new RuntimeException("Error closing resources", t);
      }
    }
  }

  public static void flush(Flushable flushable) {
    if (flushable != null) {
      try {
        flushable.flush();
      } catch (IOException e) {
        logger.error("Error flushing " + flushable, e);
      }
    }
  }

  // URI operations --------------------------------------------------------------------------------------------------

  public static String localFilename(String uri) {
    String[] path = uri.split("/");
    return path[path.length - 1];
  }

  public static boolean isURIAvailable(String uri) {
    logger.debug("isURIAvailable({})", uri);
    boolean available;
    if (uri.startsWith("string://")) {
      available = true;
    } else if (uri.startsWith("http://")) {
      available = httpUrlAvailable(uri);
    } else {
      InputStream stream = null;
      try {
        if (uri.startsWith("file:") && !uri.startsWith("file://")) {
          stream = getFileOrResourceAsStream(uri.substring("file:".length()), false);
        } else {
          if (!uri.contains("://")) {
            uri = "file://" + uri;
          }
          if (uri.startsWith("file://")) {
            stream = getFileOrResourceAsStream(uri.substring("file://".length()), false);
          }
        }
        available = (stream != null);
      } catch (FileNotFoundException e) {
        available = false;
      } finally {
        close(stream);
      }
    }
    logger.debug("isURIAvailable({}) returns {}", uri, available);
    return available;
  }

  public static String getContentOfURI(String uri) throws IOException {
    return getContentOfURI(uri, SystemInfo.getFileEncoding());
  }

  public static String getContentOfURI(String uri, String encoding) throws IOException {
    Reader reader = getReaderForURI(uri, encoding);
    return readAndClose(reader);
  }

  public static String readAndClose(Reader reader) throws IOException {
    StringWriter writer = new StringWriter();
    transferAndClose(reader, writer);
    return writer.toString();
  }

  public static String[] readTextLines(String uri, boolean includeEmptyLines) throws IOException {
    ArrayBuilder<String> builder = new ArrayBuilder<>(String.class, 100);
    BufferedReader reader = getReaderForURI(uri);
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.length() > 0 || includeEmptyLines) {
        builder.add(line);
      }
    }
    return builder.toArray();
  }

  public static BufferedReader getReaderForURI(String uri) throws IOException {
    return getReaderForURI(uri, SystemInfo.getFileEncoding());
  }

  public static BufferedReader getReaderForURI(String uri, String encoding) throws IOException {
    if (uri.startsWith("string://")) {
      return new BufferedReader(new StringReader(uri.substring("string://".length())));
    } else if (uri.startsWith("http://")) {
      return getHttpReader(new URL(uri), encoding);
    } else {
      return getFileReader(uri, encoding);
    }
  }

  public static InputStream getInputStreamForURI(String uri) throws IOException {
    Assert.notNull(uri, "uri");
    return getInputStreamForURI(uri, true);
  }

  /** Creates an InputStream from a url in String representation.
   *  @param uri the source url
   *  @param required causes the method to throw an exception if the resource is not found
   *  @return an InputStream that reads the url.
   *  @throws IOException if the url cannot be read. */
  public static InputStream getInputStreamForURI(String uri, boolean required) throws IOException {
    logger.debug("getInputStreamForURI({}, {})", uri, required);
    if (uri.startsWith("string://")) {
      String content = uri.substring("string://".length());
      return new ByteArrayInputStream(content.getBytes(SystemInfo.getCharset()));
    }
    if (isFileUri(uri)) {
      return getFileOrResourceAsStream(stripOffProtocolFromUri(uri), true);
    } else if (uri.startsWith("file:")) {
      return getFileOrResourceAsStream(uri.substring("file:".length()), true);
    } else if (uri.contains("://")) {
      return getInputStreamForURL(new URL(uri));
    } else {
      return getFileOrResourceAsStream(uri, required);
    }
  }

  public static String stripOffProtocolFromUri(String uri) {
    int index = uri.indexOf("://");
    if (index >= 0) {
      return uri.substring(index + 3);
    }
    index = uri.indexOf(":");
    if (index > 1 || (!SystemInfo.isWindows() && index > 0)) {
      return uri.substring(index + 1);
    }
    return uri;
  }

  public static boolean isFileUri(String uri) {
    return (uri.startsWith("file:") || !uri.contains("://"));
  }

  public static InputStream getInputStreamForURL(URL url) throws IOException {
    try {
      URLConnection connection = getConnection(url);
      return connection.getInputStream();
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static InputStream getInputStreamForUriReference(String localUri, String contextUri, boolean required) throws IOException {
    logger.debug("getInputStreamForUriReference({}, {})", localUri, contextUri);
    // do not resolve context for absolute URLs or missing contexts
    if (StringUtil.isEmpty(contextUri) || getProtocol(localUri) != null) {
      return getInputStreamForURI(localUri, required);
    }

    // now resolve the relative uri
    String uri = resolveRelativeUri(localUri, contextUri);

    if (localUri.startsWith("http://")) {
      return getInputStreamForURL(new URL(uri));
    }

    if (localUri.startsWith("file:") && !localUri.startsWith("file://")) {
      return getFileOrResourceAsStream(localUri.substring("file:".length()), true);
    }
    if (!localUri.contains("://") && !localUri.contains("~")) {
      localUri = "file://" + localUri;
    }
    if (localUri.startsWith("file://")) {
      return getFileOrResourceAsStream(localUri.substring("file://".length()), true);
    } else {
      throw new ConfigurationError("Can't to handle URL " + localUri);
    }
  }

  /** Returns an InputStream to a file resource on the class path.
   *  @param name the file's name
   *  @param required causes the method to throw an exception if the resource is not found
   *  @return an InputStream to the resource */
  public static InputStream getResourceAsStream(String name, boolean required) {
    logger.debug("getResourceAsStream({}, {})", name, required);
    InputStream stream = null;
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    if (contextClassLoader != null) {
      stream = contextClassLoader.getResourceAsStream(name);
    }
    if (stream == null) {
      String searchedName = (name.startsWith("/") ? name : '/' + name);
      stream = IOUtil.class.getResourceAsStream(searchedName);
    }
    if (required && stream == null) {
      throw new ConfigurationError("Resource not found: " + name);
    }
    return stream;
  }

  public static String resolveRelativeUri(String relativeUri, String contextUri) {
    logger.debug("resolveRelativeUri({}, {})", relativeUri, contextUri);
    if (contextUri == null) {
      return relativeUri;
    }
    if (".".equals(relativeUri)) {
      return contextUri;
    }
    if (isAbsoluteRef(relativeUri, contextUri)) {
      return relativeUri;
    }
    String contextProtocol = getProtocol(contextUri);
    if (contextProtocol == null || contextProtocol.equals("file")) {
      return resolveRelativeFile(getPath(contextUri), getPath(relativeUri));
    } else {
      return resolveRelativeURL(contextUri, relativeUri);
    }
  }

  public static boolean isAbsoluteRef(String uri, String contextUri) {
    if (StringUtil.isEmpty(contextUri)) // if there is no context, the URI must be absolute
    {
      return true;
    }
    // recognize Windows drive letter format like C:\
    if (uri.length() >= 3 && Character.isLetter(uri.charAt(0)) && uri.charAt(1) == ':' && uri.charAt(2) == '\\') {
      return true;
    }
    String refProtocol = getProtocol(uri);
    String ctxProtocol = getProtocol(contextUri);
    if (ctxProtocol == null) // if no context protocol is given, assume 'file'
    {
      ctxProtocol = "file";
    }
    if (!ctxProtocol.equals(refProtocol) && refProtocol != null) // if the ref has a different protocol declared than the context, its absolute
    {
      return true;
    }
    // if the protocols are 'file' and the URI starts with '/' or '~' its an absolute file URI
    return ("file".equals(ctxProtocol) && ((uri.startsWith("/") || uri.startsWith("~"))));
  }

  private static String resolveRelativeFile(String contextPath, String relativePath) {
    char firstChar = relativePath.charAt(0);
    boolean isAbsolutePath = firstChar == '/' || firstChar == File.separatorChar;
    if (isAbsolutePath || isURIAvailable(relativePath)) {
      return relativePath;
    } else {
      return new File(contextPath, relativePath).getPath();
    }
  }

  private static String resolveRelativeURL(String contextUri, String relativeUri) {
    try {
      URL contextUrl = new URL(contextUri);
      URL absoluteUrl = new URL(contextUrl, relativeUri);
      return absoluteUrl.toString();
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String getParentUri(String uri) {
    if (StringUtil.isEmpty(uri)) {
      return null;
    }
    String protocol = getProtocol(uri);
    if (protocol != null) {
      uri = uri.substring(protocol.length());
    }
    char systemSeparator = SystemInfo.getFileSeparator();
    char uriSeparator = (uri.indexOf(systemSeparator) >= 0 ? systemSeparator : '/');
    String parentUri = StringUtil.splitOnLastSeparator(uri, uriSeparator)[0];
    if (parentUri == null) {
      parentUri = ".";
    }
    parentUri += uriSeparator;
    if (protocol != null) {
      parentUri = protocol + parentUri;
    }
    return parentUri;
  }

  public static String getProtocol(String uri) {
    if (uri == null) {
      return null;
    }
    int sep = uri.indexOf("://");
    if (sep > 0) {
      return uri.substring(0, sep);
    }
    return (uri.startsWith("file:") ? "file" : null);
  }

  private static String getPath(String uri) {
    if (uri == null || ".".equals(uri)) {
      return null;
    }
    int sep = uri.indexOf("://");
    if (sep > 0) {
      return uri.substring(sep + 3);
    }
    return (uri.startsWith("file:") ? uri.substring(5) : uri);
  }

  public static PrintWriter getPrinterForURI(String uri, String encoding)
      throws FileNotFoundException, UnsupportedEncodingException {
    return getPrinterForURI(uri, encoding, false, SystemInfo.getLineSeparator(), false);
  }

  public static PrintWriter getPrinterForURI(String uri, String encoding, boolean append,
                                             final String lineSeparator, boolean autoCreateFolder)
      throws FileNotFoundException, UnsupportedEncodingException {
    File file = new File(uri);
    if (autoCreateFolder) {
      FileUtil.ensureDirectoryExists(file.getParentFile());
    }
    return new PrintWriter(new OutputStreamWriter(new FileOutputStream(uri, append), encoding)) {
      @Override
      public void println() {
        print(lineSeparator);
      }
    };
  }

  // piping streams --------------------------------------------------------------------------------------------------

  public static void transferAndClose(Reader reader, Writer writer) throws IOException {
    try {
      transfer(reader, writer);
    } finally {
      close(writer);
      close(reader);
    }
  }

  public static int transfer(Reader reader, Writer writer) throws IOException {
    int totalChars = 0;
    char[] buffer = new char[16384];

    int charsRead;
    while ((charsRead = reader.read(buffer, 0, buffer.length)) > 0) {
      writer.write(buffer, 0, charsRead);
      totalChars += charsRead;
    }
    return totalChars;
  }

  public static void transferAndClose(InputStream in, OutputStream out) throws IOException {
    try {
      transfer(in, out);
    } finally {
      close(in);
      close(out);
    }
  }

  public static int transfer(InputStream in, OutputStream out) throws IOException {
    int totalChars = 0;
    byte[] buffer = new byte[16384];

    int charsRead;
    while ((charsRead = in.read(buffer, 0, buffer.length)) != -1) {
      out.write(buffer, 0, charsRead);
      totalChars += charsRead;
    }
    return totalChars;
  }

  public static void copyFile(String srcUri, String targetUri) throws IOException {
    logger.debug("copying {} --> {}", srcUri, targetUri);
    InputStream in = null;
    OutputStream out = null;
    try {
      in = getInputStreamForURI(srcUri);
      out = openOutputStreamForURI(targetUri);
      IOUtil.transfer(in, out);
    } finally {
      close(out);
      close(in);
    }
  }

  public static OutputStream openOutputStreamForURI(String uri) throws IOException {
    if (uri.startsWith("file:")) {
      uri = uri.substring(5);
      if (uri.startsWith("//")) {
        uri = uri.substring(2);
      }
      return new FileOutputStream(uri);
    } else if (uri.contains("://")) {
      try {
        URL url = new URL(uri);
        URLConnection urlc = url.openConnection();
        return urlc.getOutputStream();
      } catch (MalformedURLException e) {
        throw new IllegalArgumentException(e);
      }
    }
    return new FileOutputStream(uri);
  }

  // Properties I/O --------------------------------------------------------------------------------------------------

  public static Map<String, String> readProperties(String filename) throws IOException {
    return readProperties(filename, SystemInfo.getFileEncoding());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Map<String, String> readProperties(String filename, String encoding) throws IOException {
    return readProperties(new OrderedMap(), filename, null, encoding);
  }

  @SuppressWarnings("rawtypes")
  public static <V> Map<String, V> readProperties(
      String filename, Converter<Map.Entry, Map.Entry> converter) throws IOException {
    return readProperties(filename, converter, SystemInfo.getFileEncoding());
  }

  @SuppressWarnings("rawtypes")
  public static <V> Map<String, V> readProperties(
      String filename, Converter<Map.Entry, Map.Entry> converter, String encoding) throws IOException {
    return readProperties(new OrderedMap<>(), filename, converter, encoding);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static <M extends Map> M readProperties(M target, String filename,
                                                  Converter<Map.Entry, Map.Entry> converter, String encoding) throws IOException {
    Reader reader = null;
    ReaderLineIterator iterator = null;
    try {
      reader = IOUtil.getReaderForURI(filename, encoding);
      iterator = new ReaderLineIterator(reader);
      String key = null;
      String value = "";
      while (iterator.hasNext()) {
        String line = iterator.next();
        line = line.trim();
        if (line.startsWith("#")) {
          continue;
        }
        boolean incomplete = (line.endsWith("\\") && line.charAt(line.length() - 2) != '\\');
        if (incomplete) {
          line = line.substring(0, line.length() - 1);
        }
        line = StringUtil.unescape(line);
        if (key != null) {
          value += line;
        } else {
          String[] assignment = ParseUtil.parseAssignment(line, "=", false);
          if (assignment != null && assignment[1] != null) {
            key = assignment[0];
            value = assignment[1];
          } else {
            continue;
          }
        }
        if (!incomplete) {
          if (converter != null) {
            Map.Entry entry = new MapEntry(key, value);
            entry = converter.convert(entry);
            target.put(entry.getKey(), entry.getValue());
          } else {
            target.put(key, value);
          }
          key = null;
          value = "";
        }
      }
    } finally {
      if (iterator != null) {
        iterator.close();
      } else {
        IOUtil.close(reader);
      }
    }
    return target;
  }

  public static void writeProperties(Map<String, ?> properties, String filename) throws IOException {
    writeProperties(properties, filename, SystemInfo.getFileEncoding());
  }

  public static void writeProperties(Map<String, ?> properties, String filename, String encoding) throws IOException {
    PrintWriter stream = null;
    try {
      stream = IOUtil.getPrinterForURI(filename, encoding);
      for (Map.Entry<String, ?> entry : properties.entrySet()) {
        stream.println(entry.getKey() + "=" + ToStringConverter.convert(entry.getValue(), ""));
      }
    } finally {
      IOUtil.close(stream);
    }
  }

  // text file im/export ---------------------------------------------------------------------------------------------

  public static void writeTextFile(String filename, String content) throws IOException {
    writeTextFile(filename, content, SystemInfo.getFileEncoding());
  }

  public static void writeTextFile(String filename, String content, String encoding) throws IOException {
    if (encoding == null) {
      encoding = SystemInfo.getCharset().name();
    }
    Writer writer = null;
    try {
      writer = new OutputStreamWriter(openOutputStreamForURI(filename), encoding);
      transfer(new StringReader(content), writer);
    } finally {
      close(writer);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  /** Returns an InputStream that reads a file. The file is first searched on the disk directories
   *  then in the class path.
   *  @param filename the name of the file to be searched.
   *  @param required when set to 'true' this causes an exception to be thrown if the file is not found
   *  @return an InputStream that accesses the file. If the file is not found and 'required' set to false, null is returned.
   *  @throws FileNotFoundException if the file cannot be found. */
  private static InputStream getFileOrResourceAsStream(String filename, boolean required) throws FileNotFoundException {
    logger.debug("getFileOrResourceAsStream({}, {})", filename, required);
    File file = new File(filename);
    if (file.exists()) {
      return new FileInputStream(filename);
    } else {
      return getResourceAsStream(filename, required);
    }
  }

  private static boolean httpUrlAvailable(String urlString) {
    try {
      URL url = new URL(urlString);
      URLConnection urlConnection = url.openConnection();
      urlConnection.connect();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private static URLConnection getConnection(URL url) throws IOException {
    URLConnection connection = url.openConnection();
    connection.setRequestProperty("User-Agent", USER_AGENT);
    connection.connect();
    return connection;
  }

  public static byte[] getBinaryContentOfUri(String uri) throws IOException {
    InputStream in = getInputStreamForURI(uri);
    ByteArrayOutputStream out = new ByteArrayOutputStream(25000);
    transfer(in, out);
    return out.toByteArray();
  }

  public static void writeBytes(byte[] bytes, File file) throws IOException {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    OutputStream out = new FileOutputStream(file);
    try {
      transfer(in, out);
    } finally {
      IOUtil.close(out);
      IOUtil.close(in);
    }
  }

  public static void copyDirectory(URL srcUrl, File targetDirectory, Filter<String> filenameFilter)
      throws IOException {
    logger.debug("copyDirectory({}, {}, {})", new Object[] {srcUrl, targetDirectory, filenameFilter});
    String protocol = srcUrl.getProtocol();
    if (protocol.equals("file")) {
      try {
        FileUtil.copy(new File(srcUrl.toURI()), targetDirectory, true, new FileByNameFilter(filenameFilter));
      } catch (URISyntaxException e) {
        throw new RuntimeException("Unexpected exception", e);
      }
    } else if (protocol.equals("jar")) {
      String path = srcUrl.getPath();
      int separatorIndex = path.indexOf("!");
      String jarPath = path.substring(5, separatorIndex); // extract jar file name
      String relativePath = path.substring(separatorIndex + 2); // extract path inside jar file
      if (!relativePath.endsWith("/")) {
        relativePath += "/";
      }
      extractFolderFromJar(jarPath, relativePath, targetDirectory, filenameFilter);
    } else {
      throw new UnsupportedOperationException("Protocol not supported: " + protocol +
          " (URL: " + srcUrl + ")");
    }
  }

  public static void extractFolderFromJar(String jarPath, String directory, File targetDirectory,
                                          Filter<String> filenameFilter) throws IOException {
    logger.debug("extractFolderFromJar({}, {}, {}, {})", new Object[] {jarPath, directory, targetDirectory, filenameFilter});
    JarFile jar = null;
    try {
      jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8));
      Enumeration<JarEntry> entries = jar.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        String name = entry.getName();
        if (name.startsWith(directory) && !directory.equals(name) && (filenameFilter == null || filenameFilter.accept(name))) {
          String relativeName = name.substring(directory.length());
          if (entry.isDirectory()) {
            File subDir = new File(targetDirectory, relativeName);
            logger.debug("creating sub directory {}", subDir);
            subDir.mkdir();
          } else {
            File targetFile = new File(targetDirectory, relativeName);
            logger.debug("copying file {} to {}", name, targetFile);
            InputStream in = jar.getInputStream(entry);
            OutputStream out = new FileOutputStream(targetFile);
            transfer(in, out);
            out.close();
            in.close();
          }
        }
      }
    } finally {
      IOUtil.close(jar);
    }
  }

  public static String[] listResources(URL url) throws IOException {
    logger.debug("listResources({})", url);
    String protocol = url.getProtocol();
    if (protocol.equals("file")) {
      try {
        String[] result = new File(url.toURI()).list();
        if (result == null) {
          result = new String[0];
        }
        logger.debug("found file resources: {}", (Object[]) result); // cast needed for avoiding varargs invocation
        return result;
      } catch (URISyntaxException e) {
        throw new RuntimeException("Unexpected exception", e);
      }
    } else if (protocol.equals("jar")) {
      String path = url.getPath();
      int separatorIndex = path.indexOf("!");
      String jarPath = path.substring(5, separatorIndex); // extract jar file name
      String relativePath = path.substring(separatorIndex + 2); // extract path inside jar file
      JarFile jar = null;
      Set<String> result;
      try {
        jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8));
        Enumeration<JarEntry> entries = jar.entries();
        result = new HashSet<>();
        while (entries.hasMoreElements()) {
          String name = entries.nextElement().getName();
          if (name.startsWith(relativePath)) {
            String entry = name.substring(relativePath.length());
            int checkSubdir = entry.indexOf("/");
            if (checkSubdir >= 0) // return only the top directory name of all sub directory entries
            {
              entry = entry.substring(0, checkSubdir);
            }
            result.add(entry);
          }
        }
        logger.debug("found jar resources: {}", result);
      } finally {
        IOUtil.close(jar);
      }
      return result.toArray(new String[result.size()]);
    } else {
      throw new UnsupportedOperationException("Protocol not supported: " + protocol +
          " (URL: " + url + ")");
    }
  }

  public static void download(URL url, File targetFile) throws IOException {
    logger.info("downloading {}", url);
    FileUtil.ensureDirectoryExists(targetFile.getParentFile());
    InputStream in = getInputStreamForURL(url);
    try {
      OutputStream out = new FileOutputStream(targetFile);
      try {
        IOUtil.transfer(in, out);
      } finally {
        IOUtil.close(out);
      }
    } finally {
      IOUtil.close(in);
    }
  }

  public static String encodeUrl(String text) {
    return encodeUrl(text, Encodings.UTF_8);
  }

  public static String encodeUrl(String text, String encoding) {
    try {
      return URLEncoder.encode(text, encoding);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Not a supported encoding: " + encoding, e);
    }
  }

  public static ImageIcon readImageIcon(String resourceName) {
    try {
      InputStream in = getInputStreamForURI(resourceName);
      if (in == null) {
        throw new FileNotFoundException("Resource not found: " + resourceName);
      }
      return new ImageIcon(ImageIO.read(in));
    } catch (Exception e) {
      throw new ConfigurationError(e);
    }
  }


  // helpers ---------------------------------------------------------------------------------------------------------

  private static BufferedReader getFileReader(String filename, String defaultEncoding)
      throws IOException {
    if (defaultEncoding == null) {
      defaultEncoding = SystemInfo.getFileEncoding();
    }
    InputStream is = getInputStreamForURI(filename);
    PushbackInputStream in = new PushbackInputStream(is, 4);
    defaultEncoding = bomEncoding(in, defaultEncoding);
    return new BufferedReader(new InputStreamReader(in, defaultEncoding));
  }

  private static BufferedReader getHttpReader(URL url, String defaultEncoding)
      throws IOException {
    try {
      URLConnection connection = getConnection(url);
      connection.connect();
      String encoding = encoding(connection, defaultEncoding);
      InputStream inputStream = connection.getInputStream();
      return new BufferedReader(new InputStreamReader(inputStream, encoding));
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String encoding(URLConnection connection, String defaultEncoding) {
    String encoding = connection.getContentEncoding();
    if (StringUtil.isEmpty(encoding)) {
      String ct = connection.getHeaderField("Content-Type");
      if (!StringUtil.isEmpty(ct)) {
        int i = ct.indexOf("charset");
        if (i >= 0) {
          encoding = ct.substring(i + "charset".length() + 1).trim();
        }
      }
    }
    if (StringUtil.isEmpty(encoding)) {
      encoding = defaultEncoding;
    }
    if (StringUtil.isEmpty(encoding)) {
      encoding = SystemInfo.getFileEncoding();
    }
    return encoding;
  }

  public static String bomEncoding(PushbackInputStream in, String defaultEncoding) throws IOException {
    int b1 = in.read();
    if (b1 == -1) {
      return defaultEncoding;
    }
    if (b1 != 0xEF) {
      in.unread(b1);
      return defaultEncoding;
    }
    int b2 = in.read();
    if (b2 == -1) {
      in.unread(b1);
      return defaultEncoding;
    }
    if (b2 != 0xBB) {
      in.unread(b2);
      in.unread(b1);
      return defaultEncoding;
    }
    int b3 = in.read();
    if (b3 == -1) {
      in.unread(b2);
      in.unread(b1);
      return defaultEncoding;
    }
    if (b3 != 0xBF) {
      in.unread(b3);
      in.unread(b2);
      in.unread(b1);
      return defaultEncoding;
    }
    return Encodings.UTF_8;
  }

}
