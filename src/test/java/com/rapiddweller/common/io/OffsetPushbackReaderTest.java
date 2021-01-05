package com.rapiddweller.common.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import org.junit.Test;

public class OffsetPushbackReaderTest {
    @Test
    public void testConstructor() {
        assertEquals(0, (new OffsetPushbackReader(Reader.nullReader())).getOffset());
        assertEquals(0, (new OffsetPushbackReader(Reader.nullReader(), 3)).getOffset());
    }

    @Test
    public void testRead() throws IOException {
        OffsetPushbackReader offsetPushbackReader = new OffsetPushbackReader(Reader.nullReader());
        assertEquals(-1, offsetPushbackReader.read());
        assertEquals(1, offsetPushbackReader.getOffset());
    }

    @Test
    public void testRead2() throws IOException {
        OffsetPushbackReader offsetPushbackReader = new OffsetPushbackReader(Reader.nullReader());
        assertEquals(-1, offsetPushbackReader.read(CharBuffer.allocate(3)));
        assertEquals(-1, offsetPushbackReader.getOffset());
    }

    @Test
    public void testRead3() throws IOException {
        OffsetPushbackReader offsetPushbackReader = new OffsetPushbackReader(Reader.nullReader());
        assertEquals(-1, offsetPushbackReader.read(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A'}));
        assertEquals(-1, offsetPushbackReader.getOffset());
    }

    @Test
    public void testUnread() throws IOException {
        OffsetPushbackReader offsetPushbackReader = new OffsetPushbackReader(Reader.nullReader());
        offsetPushbackReader.unread(1);
        assertEquals(-1, offsetPushbackReader.getOffset());
    }


    @Test
    public void testUnread4() throws IOException {
        OffsetPushbackReader offsetPushbackReader = new OffsetPushbackReader(Reader.nullReader());
        offsetPushbackReader.unread(new char[]{});
        assertEquals(0, offsetPushbackReader.getOffset());
    }


    @Test
    public void testUnread6() throws IOException {
        OffsetPushbackReader offsetPushbackReader = new OffsetPushbackReader(Reader.nullReader(), 3);
        offsetPushbackReader.unread(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A'}, 1, 3);
        assertEquals(-3, offsetPushbackReader.getOffset());
    }
}

