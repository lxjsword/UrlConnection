package html;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BinarySaver
{
	public static void main(String[] args)
	{
		try 
		{
			URL root = new URL("http://202.114.240.112/files/12210000004D31F0/yinyueshiting.baidu.com/data2/music/239786877/239786824144000128.mp3");
			System.out.println("开始下载......");
			saveBinaryFile(root);
			System.out.println("下载成功！");
		}
		catch(MalformedURLException ex)
		{
			System.err.println("It is not URL I understand.");
		}
		catch(IOException ex)
		{
			System.out.println(ex);
		}
	}

	public static void saveBinaryFile(URL u)throws IOException
	{
		URLConnection uc = u.openConnection();
		String contentType = uc.getContentType();
		int contentLength = uc.getContentLength();
		if (contentType.startsWith("text/") || contentLength == -1)	
		{
			throw new IOException("This is not a binary file.");
		}
		
		try (InputStream raw = uc.getInputStream())
		{
			InputStream in = new BufferedInputStream(raw);
			byte[] data = new byte[contentLength];
			int offset = 0;
			while(offset < contentLength)
			{
				int bytesRead = in.read(data, offset, data.length - offset);
				if (bytesRead == -1)
					break;
				offset += bytesRead;
			}
			
			if (offset != contentLength)
			{
				throw new IOException("Only read" + offset +
						"bytes; Excepted " + contentLength + " bytes.");
			}
			
			String filename = u.getFile();
			filename = filename.substring(filename.lastIndexOf('/') + 1);
			try (FileOutputStream fout = new FileOutputStream(filename))
			{
				fout.write(data);
				fout.flush();
			}
		}

	}
}
