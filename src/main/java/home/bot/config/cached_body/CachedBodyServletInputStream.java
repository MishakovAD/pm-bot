package home.bot.config.cached_body;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import lombok.SneakyThrows;

public class CachedBodyServletInputStream extends ServletInputStream {

  private final InputStream cachedBodyInputStream;

  public CachedBodyServletInputStream(byte[] cachedBody) {
    this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
  }

  @Override
  @SneakyThrows
  public boolean isFinished() {
    return cachedBodyInputStream.available() == 0;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener listener) {

  }

  @Override
  public int read() throws IOException {
    return cachedBodyInputStream.read();
  }
}
