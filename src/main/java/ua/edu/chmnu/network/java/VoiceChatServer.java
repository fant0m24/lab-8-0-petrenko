package ua.edu.chmnu.network.java;

import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VoiceChatServer {
    public static void main(String[] args) {
        final int PORT = 9555;
        final int BUFFER_SIZE = 1024;

        try (DatagramSocket socket = new DatagramSocket(PORT);
             SourceDataLine speakers = AudioSystem.getSourceDataLine(new AudioFormat(44100.0f, 16, 1, true, true))) {

            speakers.open();
            speakers.start();
            System.out.println("Voice chat server started on port " + PORT);

            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                System.out.println("Received " + packet.getLength() + " bytes from client.");
                speakers.write(packet.getData(), 0, packet.getLength());
                System.out.println("Audio played on speakers.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
