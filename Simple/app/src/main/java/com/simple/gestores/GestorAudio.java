package com.simple.gestores;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by UO244588 on 09/10/2017.
 */

public class GestorAudio implements MediaPlayer.OnPreparedListener {
    public static final int SONIDO_DISPARO_NAVE = 1;
    public static final int SONIDO_DISPARO_ENEMIGO = 2;
    public static final int SONIDO_NAVE_HIT = 3;
    public static final int SONIDO_EXPLOSION_AVION= 4;
    // Pool de sonidos, para efectos de sonido.
    // Suele fallar el utilizar ficheros de sonido demasiado grandes
    private SoundPool poolSonidos;
    private HashMap<Integer, Integer> mapSonidos;
    private Context contexto;
    // Media Player para bucle de sonido de fondo.
    private MediaPlayer sonidoAmbiente;
    private AudioManager gestorAudio;


    private static GestorAudio instancia = null;

    public static GestorAudio getInstancia(Context contexto,
                                           int idMusicaAmbiente) {
        synchronized (GestorAudio.class) {
            if (instancia == null) {
                instancia = new GestorAudio();
                instancia.initSounds(contexto, idMusicaAmbiente);
            }
            return instancia;
        }
    }

    public static GestorAudio getInstancia() {
        return instancia;
    }

    private GestorAudio() {
    }


    private void initSounds(Context contexto, int idMusicaAmbiente) {
        this.contexto = contexto;
        poolSonidos = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        mapSonidos = new HashMap<Integer, Integer>();
        gestorAudio = (AudioManager) contexto
                .getSystemService(Context.AUDIO_SERVICE);
        sonidoAmbiente = MediaPlayer.create(contexto, idMusicaAmbiente);
        sonidoAmbiente.setLooping(true);
        sonidoAmbiente.setVolume(1, 1);

    }

    public void reproducirMusicaAmbiente() {
        try {
            if (!sonidoAmbiente.isPlaying()) {
                try {
                    sonidoAmbiente.setOnPreparedListener(this);
                    sonidoAmbiente.prepareAsync();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public void pararMusicaAmbiente() {
        if (sonidoAmbiente.isPlaying()) {
            sonidoAmbiente.stop();
        }
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }


    public void registrarSonido(int index, int SoundID) {
        mapSonidos.put(index, poolSonidos.load(contexto, SoundID, 1));
    }

    public void reproducirSonido(int index) {
        float volumen =
                gestorAudio.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumen =
                volumen / gestorAudio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        poolSonidos.play(
                (Integer) mapSonidos.get(index),
                volumen, volumen, 1, 0, 1f);
    }

}
