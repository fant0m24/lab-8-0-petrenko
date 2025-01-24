package ua.edu.chmnu.network.java;

import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class VoiceChatClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 9555;
        final int BUFFER_SIZE = 1024;

        try (DatagramSocket socket = new DatagramSocket();
             TargetDataLine microphone = AudioSystem.getTargetDataLine(new AudioFormat(44100.0f, 16, 1, true, true))) {

            microphone.open();
            microphone.start();
            System.out.println("Microphone opened. Recording audio...");

            byte[] buffer = new byte[BUFFER_SIZE];
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                System.out.println("Sending " + bytesRead + " bytes to server...");

                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, serverAddress, SERVER_PORT);
                socket.send(packet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
