package com.pixel_pioneer.sound;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Random;

public class SoundEngine implements MetaEventListener {

    private final Sequencer sequencer;
    private final Synthesizer synthesizer;
    private static final String[] ALL_GAME_TRACKS = {
            "sound/music/game_track.mid",
            "sound/music/game_track2.mid",
            "sound/music/game_track3.mid",
            "sound/music/game_track4.mid",
    };

    private final Random random = new Random();

    public SoundEngine() {
        try {
            sequencer = MidiSystem.getSequencer(false);
            sequencer.open();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        sequencer.addMetaEventListener(this);

    }

    private void setVolume(double gain) {
        MidiChannel[] channels = synthesizer.getChannels();
        ShortMessage volumeMessage = new ShortMessage();
        int i = 0;
        for (MidiChannel channel : channels) {
            int volume = (int) (gain * 127.0);
            channel.controlChange(7, volume);
            try {
                volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7, volume);
                MidiSystem.getReceiver().send(volumeMessage, -1);
            } catch (MidiUnavailableException | InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }

    public void playOw() {
        // if clip.isRunning() clip.stop()
        // clip.setFramePosition(0);
        File owFile = new File("sound/ow.wav");
        AudioInputStream audioStream;
        try {
            audioStream = AudioSystem.getAudioInputStream(owFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip owClip;
        try {
            owClip = (Clip) AudioSystem.getLine(info);
            owClip.open(audioStream);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        owClip.start();
    }

    public void playBackgroundMusic() {
        playNextSong();
    }

    public void playDeadSong() {
        playSong("sound/music/dead.mid");
    }

    private void playSong(String file) {
        sequencer.stop();
        sequencer.setTickPosition(0);
        try {
            sequencer.getTransmitter().setReceiver(null);
            InputStream is =
                    new BufferedInputStream(new FileInputStream(file));
            sequencer.setSequence(is);
        } catch (InvalidMidiDataException | IOException | MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        sequencer.start();
        try {
            sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void playNextSong() {
        String track = ALL_GAME_TRACKS[random.nextInt(ALL_GAME_TRACKS.length)];
        playSong(track);
    }

    @Override
    public void meta(MetaMessage meta) {
        double midiVolume = 0.2;
        setVolume(midiVolume);
        if (meta.getType() == 47) {
            sequencer.stop();
            playNextSong();
        }
    }
}
