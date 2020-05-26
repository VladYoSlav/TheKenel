package com.example.horrorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SoundPool soundPool;

    RoomView[] roomviews = new RoomView[100];

    Animation fadein, fadeout, go_to_room, start_game_animation, pre_go_to_room, show_dialog_window,
        rotate_player, pre_rotate_player_right, pre_rotate_player_left, press_to_continue_anim, start_screen_animation, start_text_anim;

    MediaPlayer mediaPlayer;

    VideoView video_layout, start_screen_video_layout;

    FrameLayout dialog_window,  main_layout, start_screen;

    Button start_game_button, turn_right, turn_left, go_button, close_game_button;

    LinearLayout buttons_bar, start_game_buttons_bar;

    TextView dialog_text, press_to_continue, start_text;

    Character player = new Character();

    String uriPath;

    Uri uri;

    View black_screen;

    Random random;

    int car_engine_sound1, car_door_sound1, leaves_footsteps1, leaves_footsteps2, leaves_footsteps3, breath_sound1,
        scream_sound1, heart_sound1, ringtone_sound1, cough_sound1, camera_on_sound1, sigh_sound1, notebook_page_sound1,
        stone_footsteps1, stone_footsteps2, stone_footsteps3, branch_sound1, pigeon_sound1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initializeRoomViews();

        turn_right = (Button)findViewById(R.id.turn_right);
        turn_left = (Button)findViewById(R.id.turn_left);
        go_button = (Button)findViewById(R.id.go);
        dialog_window = (FrameLayout)findViewById(R.id.dialog_window);
        start_screen = (FrameLayout)findViewById(R.id.start_screen);
        start_game_button = (Button)findViewById(R.id.start_game_button);
        dialog_text = (TextView)findViewById(R.id.dialog_text);
        buttons_bar = (LinearLayout)findViewById(R.id.buttons_bar);
        press_to_continue = (TextView)findViewById(R.id.press_to_continue);
        main_layout = (FrameLayout)findViewById(R.id.main_layout);
        video_layout = (VideoView) findViewById(R.id.video_layout);
        start_screen_video_layout = (VideoView) findViewById(R.id.start_screen_video_layout);
        black_screen = (View) findViewById(R.id.black_screen);
        start_game_buttons_bar = (LinearLayout)findViewById(R.id.start_menu_buttons_bar);
        close_game_button = (Button)findViewById(R.id.close_game_button);
        start_text = (TextView) findViewById(R.id.start_text);

        random = new Random();

        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        go_to_room = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.go_to_room);
        start_game_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_game_animation);
        pre_go_to_room = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pre_go_to_room);
        show_dialog_window = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_dialog_window);
        rotate_player = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_player);
        pre_rotate_player_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pre_rotate_player);
        pre_rotate_player_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pre_rotate_player);
        press_to_continue_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.press_to_continue);
        start_screen_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_screen_animation);
        start_text_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_text_anim);

        DelayedPrinter.press_to_continue = press_to_continue;
        DelayedPrinter.press_to_continue_anim = press_to_continue_anim;
        DelayedPrinter.dialog_window = dialog_window;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attributes)
                    .build();
        }
        else{
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }


        turn_right.setOnClickListener(this);
        turn_left.setOnClickListener(this);
        go_button.setOnClickListener(this);
        dialog_window.setOnClickListener(this);


        car_engine_sound1 = soundPool.load(this, R.raw.car_engine1, 1);
        car_door_sound1 = soundPool.load(this, R.raw.car_door1, 1);
        leaves_footsteps1 = soundPool.load(this, R.raw.footsteps_leaves1, 1);
        leaves_footsteps2 = soundPool.load(this, R.raw.footsteps_leaves2, 1);
        leaves_footsteps3 = soundPool.load(this, R.raw.footsteps_leaves3, 1);
        breath_sound1 = soundPool.load(this, R.raw.breath1, 1);
        scream_sound1 = soundPool.load(this, R.raw.scream1, 1);
        heart_sound1 = soundPool.load(this, R.raw.heart1, 1);
        ringtone_sound1 = soundPool.load(this, R.raw.ringtone, 1);
        cough_sound1 = soundPool.load(this, R.raw.cough1, 1);
        camera_on_sound1 = soundPool.load(this, R.raw.camera_on, 1);
        sigh_sound1 = soundPool.load(this, R.raw.sigh1, 1);
        notebook_page_sound1 = soundPool.load(this, R.raw.notebook_page, 1);
        stone_footsteps1 = soundPool.load(this, R.raw.footsteps_stone1, 1);
        stone_footsteps2 = soundPool.load(this, R.raw.footsteps_stone2, 1);
        stone_footsteps3 = soundPool.load(this, R.raw.footsteps_stone3, 1);
        branch_sound1 = soundPool.load(this, R.raw.branch1, 1);
        pigeon_sound1 = soundPool.load(this, R.raw.pigeon1, 1);


        mediaPlayer = MediaPlayer.create(this, R.raw.main_menu_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //black_screen.startAnimation(start_screen_animation);
        start_text.startAnimation(start_text_anim);

        close_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        start_text_anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                start_game_buttons_bar.setVisibility(View.INVISIBLE);
                start_text.setVisibility(View.VISIBLE);
                start_text.setText("Подготовили\nШальнев Владислав\nКоротченко Владислав\nТарарин Александр\nСпециально для IT Школа SAMSUNG\n\nНекоторые материалы были взяты\nс канала Дмитрия Масленникова");
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                start_game_buttons_bar.setVisibility(View.VISIBLE);
                start_text.setVisibility(View.INVISIBLE);
                black_screen.startAnimation(start_screen_animation);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        start_screen_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                uriPath = "android.resource://"+getPackageName()+"/"+R.raw.start_screen;
                uri = Uri.parse(uriPath);
                start_screen_video_layout.setVideoURI(uri);
                start_screen_video_layout.start();
                start_screen_video_layout.setVisibility(View.VISIBLE);
                start_game_buttons_bar.setEnabled(false);
                start_game_buttons_bar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                start_game_buttons_bar.setVisibility(View.VISIBLE);
                start_game_buttons_bar.setEnabled(true);
                start_game_buttons_bar.startAnimation(fadein);
                black_screen.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_game_button.setEnabled(false);
                black_screen.startAnimation(start_game_animation);
            }
        });

        start_game_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                black_screen.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                start_screen_video_layout.setVideoURI(null);
                start_screen_video_layout.setVisibility(View.INVISIBLE);
                mediaPlayer.release();
                mediaPlayer = null;
                setRoomView(1);
                black_screen.startAnimation(go_to_room);
                start_screen.setVisibility(View.INVISIBLE);
                main_layout.setVisibility(View.VISIBLE);
                black_screen.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        video_layout.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                video_layout.start();
            }
        });
        start_screen_video_layout.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                start_screen_video_layout.start();
            }
        });

        show_dialog_window.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                press_to_continue.clearAnimation();
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                //press_to_continue.startAnimation(press_to_continue_anim);
                dialog_window.setEnabled(true);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        pre_rotate_player_right.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                disableMovementButtons();
                black_screen.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                hideMovementButtons();
                setRoomView(roomviews[player.roomview].turn_right);
                video_layout.setVideoURI(null);
                video_layout.setVisibility(View.INVISIBLE);
                black_screen.startAnimation(rotate_player);
                black_screen.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        pre_rotate_player_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                disableMovementButtons();
                black_screen.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                hideMovementButtons();
                setRoomView(roomviews[player.roomview].turn_left);
                video_layout.setVideoURI(null);
                video_layout.setVisibility(View.INVISIBLE);
                black_screen.startAnimation(rotate_player);
                black_screen.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        rotate_player.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(roomviews[player.roomview].playAction) playCurrentRoomViewAction();
                black_screen.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                black_screen.setVisibility(View.INVISIBLE);
                showDialogWindow();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        pre_go_to_room.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                disableMovementButtons();
                black_screen.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(roomviews[player.roomview].sound != null) randomGoSound(roomviews[player.roomview].sound);
                video_layout.setVideoURI(null);
                video_layout.setVisibility(View.INVISIBLE);
                black_screen.setVisibility(View.INVISIBLE);
                hideMovementButtons();
                setRoomView(roomviews[player.roomview].go);
                black_screen.startAnimation(go_to_room);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        go_to_room.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                if(roomviews[player.roomview].playAction) playCurrentRoomViewAction();

                black_screen.setVisibility(View.VISIBLE);
                disableMovementButtons();
                hideMovementButtons();
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                showDialogWindow();
                black_screen.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void randomGoSound(String type){
        switch (type){
            case "engine":
                playSound(car_engine_sound1);
                break;
            case "car_door":
                playSound(car_door_sound1);
                break;
            case "leaves_footsteps":
                int[] leaves_sounds = {leaves_footsteps1, leaves_footsteps2, leaves_footsteps3};
                int leaves_sound = leaves_sounds[random.nextInt(leaves_sounds.length)];
                playSound(leaves_sound);
                break;
            case "stone_footsteps":
                int[] stone_sounds = {stone_footsteps1, stone_footsteps2, stone_footsteps3};
                int stone_sound = stone_sounds[random.nextInt(stone_sounds.length)];
                playSound(stone_sound);
                break;

        }
    }
    public void playSound(int sound){
        soundPool.pause(soundPool.play(sound, 0, 0, 0, 0, 1));
        soundPool.play(sound, 2, 2, 0, 0, 1);
    }
    public void playCurrentRoomViewAction(){
        switch (player.roomview){
            case 1:
                main_layout.setBackgroundColor(0);
                uriPath = "android.resource://"+getPackageName()+"/"+R.raw.road;
                uri = Uri.parse(uriPath);
                video_layout.setVideoURI(uri);
                video_layout.start();
                video_layout.setVisibility(View.VISIBLE);
                if(mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(this, R.raw.engine_road_anim);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();

                /*Button button = new Button(this);
                button.setText("кнопка");
                button.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                main_layout.addView(button);*/
                break;
            case 2:
                if(mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.crickets);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
                break;
            case 7:
                roomviews[7].dialogNumber = 0;
                break;
            case 8:
                roomviews[7].go = 35;
                roomviews[7].dialog = new String[]{
                        "Может быть стоит просто уехать?"
                };
                if(roomviews[9].go == 0) {
                    playSound(breath_sound1);
                }
                break;
            case 14:
                main_layout.setBackgroundColor(0);
                uriPath = "android.resource://"+getPackageName()+"/"+R.raw.dead_body;
                uri = Uri.parse(uriPath);
                video_layout.setVideoURI(uri);
                video_layout.start();
                video_layout.setVisibility(View.VISIBLE);
                break;
            case 21:
                main_layout.setBackgroundColor(0);
                black_screen.startAnimation(pre_go_to_room);
                break;
            case 30:
                roomviews[32].go = 27;
                if(roomviews[30].dialogNumber < roomviews[30].dialog.length){
                    playSound(pigeon_sound1);
                }
                break;
            case 32:
                main_layout.setBackgroundColor(0);
                black_screen.startAnimation(pre_go_to_room);
                break;
            case 33:
                roomviews[32].go = 31;
                break;
            case 34:
                if(mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(this, R.raw.main_menu_music);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 35:
                if(mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(this, R.raw.main_menu_music);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 36:
                main_layout.setBackgroundColor(0);
                black_screen.startAnimation(pre_go_to_room);
                break;
        }
    }
    public void playCurrentRoomViewAfterDialogAction(){
        switch (player.roomview){
            case 1:
                roomviews[1].go = 2;
                if(mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                black_screen.startAnimation(pre_go_to_room);
                break;
            case 8:
                roomviews[8].turn_left = 36;
                if(roomviews[9].go == 0) {
                    roomviews[8].go = 9;
                    black_screen.startAnimation(pre_go_to_room);
                }
                break;
            case 9:
                if(roomviews[8].go == 0){
                    roomviews[9].go = 8;
                    roomviews[8].playAction = false;
                    roomviews[8].playAfterDialogAction = false;
                    black_screen.startAnimation(pre_go_to_room);
                }
                else{
                    roomviews[9].image = R.drawable.dummy_anim2_with_note;
                    roomviews[8].image = R.drawable.view_7_1;
                    roomviews[8].dialog = new String[] {
                            "Ну и шутники блин."
                    };
                    roomviews[8].turn_right = 10;
                    roomviews[8].dialogNumber = 0;
                    playSound(scream_sound1);
                    roomviews[9].dialog = new String[] {
                            "Черт возьми!",
                            "Что это? Похоже на какой-то грязный блокнот . . .",
                            "Он . . . пуст? В нём ничего нет, странно.",
                            "Возьму с собой на всякий случай . . ."
                    };
                    roomviews[9].dialogNumber = 0;
                    showDialogWindow();
                    setRoomView(9);
                    roomviews[8].go = 0;
                }
                break;
        }
    }
    public void playDialogStringAction(){
        int number = roomviews[player.roomview].dialogNumber;
        switch (player.roomview){
            case 1:
                if(number == 0){
                    playSound(ringtone_sound1);
                }
                else if(number == 1){
                    soundPool.autoPause();
                }
                else if(number == 13){
                    playSound(sigh_sound1);
                }
                else if(number == 15){
                    playSound(ringtone_sound1);
                }
                else if(number == 16){
                    soundPool.autoPause();
                }
                else if(number == 21){
                    playSound(cough_sound1);
                }
                break;
            case 5:
                if(number == 0){
                    playSound(camera_on_sound1);
                }
                break;
            case 9:
                if(number == 2){
                    roomviews[9].image = R.drawable.dummy_anim2;
                    setRoomView(9);
                    playSound(notebook_page_sound1);
                }
                break;
            case 14:
                if(number == 0){
                    playSound(heart_sound1);
                }
                break;
            case 27:
                if(number == 1){
                    playSound(branch_sound1);
                }
                break;

        }

    }
    public void showDialogWindow(){
        if(DelayedPrinter.isPrinting){
            DelayedPrinter.stopPrinting();
        }
        else{
            if(roomviews[player.roomview].dialog.length != 0 && !(roomviews[player.roomview].dialogNumber == roomviews[player.roomview].dialog.length)) {
                dialog_text.setText(roomviews[player.roomview].dialog[roomviews[player.roomview].dialogNumber]);
                DelayedPrinter.Word word = new DelayedPrinter.Word(50, 1, dialog_text.getText().toString());
                DelayedPrinter.printText(word, dialog_text);
                dialog_window.setEnabled(false);
                dialog_window.setVisibility(View.VISIBLE);
                press_to_continue.setVisibility(View.INVISIBLE);
                dialog_window.startAnimation(show_dialog_window);
                playDialogStringAction();
                roomviews[player.roomview].dialogNumber++;
            }
            else hideDialogWindow();
        }
    }
    public void hideDialogWindow() {
        dialog_window.setEnabled(false);
        dialog_window.setVisibility(View.INVISIBLE);
        showMovementButtons();
        if(roomviews[player.roomview].playAfterDialogAction) playCurrentRoomViewAfterDialogAction();
    }
    public void showMovementButtons(){
        if(roomviews[player.roomview].go != 0){
            go_button.setVisibility(View.VISIBLE);
            go_button.setEnabled(true);
            go_button.startAnimation(fadein);
        }
        if(roomviews[player.roomview].turn_left != 0){
            turn_left.setVisibility(View.VISIBLE);
            turn_left.setEnabled(true);
            turn_left.startAnimation(fadein);
        }
        if(roomviews[player.roomview].turn_right != 0){
            turn_right.setVisibility(View.VISIBLE);
            turn_right.setEnabled(true);
            turn_right.startAnimation(fadein);
        }
    }
    public void hideMovementButtons(){
        go_button.clearAnimation();
        turn_left.clearAnimation();
        turn_right.clearAnimation();
        go_button.setVisibility(View.INVISIBLE);
        turn_left.setVisibility(View.INVISIBLE);
        turn_right.setVisibility(View.INVISIBLE);
    }
    public void disableMovementButtons(){
        go_button.setEnabled(false);
        turn_left.setEnabled(false);
        turn_right.setEnabled(false);
    }
    public void setRoomView(Integer number){
        main_layout.setBackgroundDrawable(getResources().getDrawable(roomviews[number].image));
        player.roomview = number;
    }
    public void initializeRoomViews(){
        roomviews[1] = new RoomView(0, 0, 0, R.drawable.road_anim1, new String[] {
                "*звонок*",
                "Алло?",
                "Категорически приветствую. Вы тот самый охотник за духами?",
                "Да-да, это я, специалист по ловле привидений.",
                "Дмитрий Лопухов, верно?",
                "Да-да, верно. Дмитрий Лопухов.",
                "Так случилось, что у моего соседа поселилось привидение. Он сам отказывается это признавать, можете проверить?",
                ". . .",
                "НЕТ, Я не проверяю дома людей без их личного согласия!",
                "Я заплачу крупную сумму.",
                "Мне без разницы, сколько вы заплатите, я соблюдаю закон!",
                "Но . . . ",
                ". . . Давайте завтра поговорим, я сейчас . . . занят. Да, занят. До свидания.",
                "*кладёт трубку*",
                "Как же меня это достало . . . Эх . . .",
                "*звонок*",
                "АЛО?",
                "Ало, братуха, тут это, по твоей части. Тип есть замок, который нужно проверить.",
                "Коля, друган, ты ли это? Когда это ты в последнее время что-то хорошее говорил?",
                "Ты же знаешь, что никакой я не охотник за привидениями и это всё ради заработка на дурачках поехавших.",
                "Человечек заплатит 10 тыщ евро за проверку.",
                "*кашель*",
                "Сколько? . .",
                "Димон, ну это, записывай адрес . . ."
        },true, true, "engine");
        roomviews[2] = new RoomView(4, 3, 0, R.drawable.view_1, new String[] {
                "Похоже я на месте . . . Возьму магнитофон. Так, может запишу что-то . . . Да и камеру тоже возьму.",

        },true, false, "engine");
        roomviews[3] = new RoomView(0, 0, 2, R.drawable.view_2, new String[] {
                "Не думаю, что смогу как-то пройти здесь",
        },false, false, "engine");
        roomviews[4] = new RoomView(5, 0, 0, R.drawable.view_3, new String[] {
                "Я вижу дыру в стене, возможно мне удастся проникнуть через неё.",
        },false, false, "car_door");
        roomviews[5] = new RoomView(8, 7, 6, R.drawable.view_4, new String[] {
                "Камеру включил, теперь можно и идти.",
        },false, false, "leaves_footsteps");
        roomviews[6] = new RoomView(0, 5, 7, R.drawable.view_5, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[7] = new RoomView(0, 6, 5, R.drawable.view_6, new String[] {
        },true, false, null);
        roomviews[8] = new RoomView(0, 0, 0, R.drawable.view_7, new String[] {
                "Черт возьми! Что это?",
                "А, похоже это всего лишь манекен.",
                "Видно какой-то шутник захотел меня напугать.",
                ". . .",
        },true, true, "leaves_footsteps");
        roomviews[9] = new RoomView(0, 0, 0, R.drawable.dummy_anim1, new String[] {
                "Покажи-ка свое личико . . .",
        },false, true, "leaves_footsteps");
        roomviews[10] = new RoomView(17, 8, 11, R.drawable.view_11, new String[] {
                "Там какая-то постройка.",
        },false, false, "leaves_footsteps");
        roomviews[11] = new RoomView(12, 10, 0, R.drawable.view_8, new String[] {
                "Что это висит?",
        },false, false, "leaves_footsteps");
        roomviews[12] = new RoomView(14, 13, 0, R.drawable.view_9, new String[] {
                "Твою ж . . .",
        },false, false, "leaves_footsteps");
        roomviews[13] = new RoomView(8, 0, 12, R.drawable.view_10, new String[] {},false, false, "leaves_footsteps");
        roomviews[14] = new RoomView(0, 15, 0, R.drawable.view_14, new String[] {
                "Да что тут блин творится?",
                "Неужели та голова принадлежала . . .",
                "Чертовы сатанисты . . .",
                "Просто забудь, Дима, просто забудь."
        },true, false, "leaves_footsteps");
        roomviews[15] = new RoomView(24, 16, 14, R.drawable.view_12, new String[] {
                "Тут можно пройти . . ."
        },false, false, "leaves_footsteps");
        roomviews[16] = new RoomView(13, 0, 15, R.drawable.view_13, new String[] {},false, false, "leaves_footsteps");
        roomviews[17] = new RoomView(18, 22, 0, R.drawable.view_15, new String[] {
                "Это какой-то подвальчик . . ."
        },false, false, "stone_footsteps");
        roomviews[18] = new RoomView(19, 20, 20, R.drawable.view_16, new String[] {
        },false, false, "stone_footsteps");
        roomviews[19] = new RoomView(0, 21, 21, R.drawable.view_17, new String[] {
                "Это похоже на какую-то яму, в которую скидывали узников . . ."
        },false, false, "stone_footsteps");
        roomviews[20] = new RoomView(22, 18, 18, R.drawable.view_18, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[21] = new RoomView(20, 0, 0, R.drawable.view_19, new String[] {
        },true, false, "stone_footsteps");
        roomviews[22] = new RoomView(10, 0, 17, R.drawable.view_20, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[23] = new RoomView(16, 24, 24, R.drawable.view_21, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[24] = new RoomView(26, 23, 23, R.drawable.view_22, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[25] = new RoomView(23, 26, 26, R.drawable.view_23, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[26] = new RoomView(27, 25, 25, R.drawable.view_24, new String[] {
                "Почему у меня появляется ощущение, что тут реально кто-то есть?"
        },false, false, "leaves_footsteps");
        roomviews[27] = new RoomView(30, 28, 0, R.drawable.view_27, new String[] {
                "Невероятно жуткое ощущение . . .",
                "Что это было?"
        },false, false, "leaves_footsteps");
        roomviews[28] = new RoomView(0, 29, 27, R.drawable.view_26, new String[] {
                "Тут ничего нет . . ."
        },false, false, "leaves_footsteps");
        roomviews[29] = new RoomView(25, 0, 28, R.drawable.view_25, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[30] = new RoomView(0, 31, 32, R.drawable.view_28, new String[] {
                "Хе-хе . . . Одинокий голубь на жердочке . . .",
                "Он . . . Неживой . . ."
        },true, false, "leaves_footsteps");
        roomviews[31] = new RoomView(33, 0, 30, R.drawable.view_29, new String[] {
        },false, false, "leaves_footsteps");
        roomviews[32] = new RoomView(0, 0, 0, R.drawable.view_29, new String[] {
        },true, false, "leaves_footsteps");
        roomviews[33] = new RoomView(34, 32, 32, R.drawable.view_30, new String[] {
                "Тут виднеется здание . . . Наконец-то."
        },true, false, null);
        roomviews[34] = new RoomView(0, 0, 0, R.drawable.to_be_continued, new String[] {
        },true, false, "leaves_footsteps");
        roomviews[35] = new RoomView(0, 0, 0, R.drawable.the_end, new String[] {
        },true, false, "leaves_footsteps");
        roomviews[36] = new RoomView(5, 0, 0, R.drawable.view_7_1, new String[] {
        },true, false, "leaves_footsteps");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_right:
                black_screen.startAnimation(pre_rotate_player_right);
                break;
            case R.id.turn_left:
                black_screen.startAnimation(pre_rotate_player_left);
                break;
            case R.id.go:
                black_screen.startAnimation(pre_go_to_room);
                break;
            case R.id.dialog_window:
                showDialogWindow();
                break;
        }
    }
}
class Character{
    Integer roomview;
}
class RoomView {
    String[] dialog;
    Integer dialogNumber;
    Integer go;
    Integer turn_left;
    Integer turn_right;
    Boolean playAction;
    Boolean playAfterDialogAction;
    String sound;
    int image;
    public RoomView (Integer go, Integer turn_left, Integer turn_right, int image, String[] dialog, Boolean playAction, Boolean playAfterDialogAction, String sound){
        this.go = go;
        this.turn_left = turn_left;
        this.turn_right = turn_right;
        this.image = image;
        this.dialog = dialog;
        this.dialogNumber = 0;
        this.playAction = playAction;
        this.playAfterDialogAction = playAfterDialogAction;
        this.sound = sound;
    }
}
class DelayedPrinter {
    static TextView press_to_continue;
    static Animation press_to_continue_anim;
    static FrameLayout dialog_window;
    static boolean canPrint = true;
    static boolean isPrinting = false;

    public static void printText(final Word word, final TextView textView) {
        if(canPrint){
            isPrinting = true;
            if(word.currentCharacterIndex == 0) textView.setText("");
            Random random = new Random(System.currentTimeMillis());
            int currentRandOffset = random.nextInt(word.offset);
            boolean addOrSubtract = random.nextBoolean();
            long finalDelay = addOrSubtract ? word.delayBetweenSymbols + currentRandOffset : word.delayBetweenSymbols - currentRandOffset;
            if (finalDelay < 0) finalDelay = 0;

            textView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String charAt = String.valueOf(word.word.charAt(word.currentCharacterIndex));
                    ++word.currentCharacterIndex;
                    textView.setText(textView.getText() + charAt);
                    if (word.currentCharacterIndex >= word.word.length()){
                        press_to_continue.startAnimation(press_to_continue_anim);
                        word.currentCharacterIndex = 0;
                        isPrinting = false;
                        return;
                    }
                    printText(word, textView);
                }
            }, finalDelay);
        }
        else{
            textView.setText(word.word);
            press_to_continue.startAnimation(press_to_continue_anim);
            word.currentCharacterIndex = 0;
            canPrint = true;
            isPrinting = false;
            return;
        }

    }
    public static void stopPrinting(){
        canPrint = false;
    }

    public static class Word {

        private long delayBetweenSymbols;
        private String word;
        private int offset;
        private int currentCharacterIndex;

        public Word(long delayBetweenSymbols, int offset, String word) {
            if (delayBetweenSymbols < 0) throw new IllegalArgumentException("Delay can't be < 0");
            this.delayBetweenSymbols = delayBetweenSymbols;
            this.word = word;
            this.offset = offset;
        }
    }

}
