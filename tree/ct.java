//DEPS javazoom:jlayer:1.0.1
                                /**\
                               |****|
                                \**/
                                //\\
                               //||\\
                              //||||\\
                             //||||||\\
                            //||||||||\\
                           //||||||||||\\
                          //||********||\\
                         //|||*J'BANG*|||\\
                        //||||********||||\\
                       //||||||||||||||||||\\
                      //||||||||||||||||||||\\
                     package christmas;  import
                    java.io.*;import java.util.*;
                   import java.nio.file.*;  import
                  javazoom.jl.player.Player; import
                 java.net.*; public final /**/ class
                ct { public static void main(String[]
               arg) throws /**/ IOException{playMus();
              printTree();}public static void playMus()
             { Runnable songThread =()->{try{ var buffer
            = new BufferedInputStream ( new java.net.URL(
           "https://git.io/JDbRr").openStream()); var play
          = new Player(buffer);play.play();}catch(Exception
        e) { System.out.println(e);}}; Thread t = new Thread
       (songThread);t.start();}public static void printTree()
      throws IOException {  Path filePath = Paths.get( System.
     getProperty("user.dir" ),"ct.java"); List<String> allLines
                         = Files.readAllLines
                         (filePath);allLines.
                         remove(0);for(String
                         line :  allLines ) {
                System.out.println (line); try { Thread
                .sleep(500);}catch(InterruptedException
                e ){} /* */ } System.exit(0); /* */ } }
  