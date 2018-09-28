package byte_streams;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class shows input output operations on a fairly low level.
 * Whatever comes into the stream will be turned into byte size chunks represented
 * by an integer type. See stdout.
 * It offers three interchangeable SOURCES (file, URL, stream) while writing bytes
 * to the same target file overwriting previous content every time.
 *
 */

public class Controller {

    private static byte[] buffer;                                            // Byte buffer for storing data between reading and writing
    private static int buffer_size;                                          // The amount of data being read each cycle is stored in this one
    private static File target_file;                                         // The file to write to
    private static final int BUFFER_CAPACITY = 1024;                         // Defines how many bytes can be written to the buffer at most
    private static InputStream stream_source = System.in;                   // A source of the type stream (if app reads End Of Stream (EOS) sequence it will stop reading)
    private static File file_source = new File("C:\\Users\\user\\Pictures\\your.jpg"); // A source of the type file
    private static URL net_source;                                           // A source of the type URL

    static {                                                                  // Initialization of URL objects throw an exception
        try {
            net_source = new URL("http://anydomain.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws URISyntaxException {
        target_file = new File("C:\\Users\\user\\Pictures\\ByteTest.txt");
        buffer = new byte[BUFFER_CAPACITY];
        buffer_size = 0;

        //+++++++++++++++++++++++++++++++++++++
        // new FileInputStream(file_source);                                    // If you read/write a jpg-image, just change the target_file suffix from 'txt' to 'jpg'
        // net_source.openStream();
        // System.in;
        //+++++++++++++++++++++++++++++++++++++

        try(InputStream is = new FileInputStream(file_source); /*<- code snippets here*/ FileOutputStream fos = new FileOutputStream(target_file)){
           out: while((buffer_size = is.read(buffer, 0, BUFFER_CAPACITY)) != -1){   // True until read() reaches an end of stream indication (works for resources (files & URLs))
                fos.write(buffer, 0, buffer_size);                                  // Writes to the output stream
                fos.flush();                                                            // Flushes the output stream into the file
                for(int i = 0; i < buffer_size; i++ ){
                    if(buffer[i] == 69 && buffer[i+1] == 79 && buffer[i+2] == 83){  // Checks the content contains an EOS sequence
                        break out;                                                  // Breaks out of the while loop and continues with the code
                        //return;                                                   // Terminates the whole application gracefully
                    } // end of if()
                    System.out.print(buffer[i] + " ");                              // Write the content of 'buffer' into a line
                } // end of for()
               System.out.print(System.lineSeparator());                            // Starts a new line after the buffer content has been completely printed out
            } // end of while
        } catch  (Exception e){
            System.out.println("InputStream says: " + e);

        }
        System.out.println("Everything as intended, " + System.getProperties().getProperty("user.name").toUpperCase() + " ?");

    } // end of main

} //end of class
