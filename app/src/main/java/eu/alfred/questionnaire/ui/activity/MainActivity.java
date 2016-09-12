package eu.alfred.questionnaire.ui.activity;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import eu.alfred.questionnaire.R;
import eu.alfred.questionnaire.util.Constants;
import eu.alfred.questionnaire.util.Prefs;
import eu.alfred.questionnaire.util.StringUtils;
import eu.alfred.ui.BackToPAButton;
import eu.alfred.ui.CircleButton;

public class MainActivity extends BaseActivity {

    private static final String TAG = "TAGMainActivity";

    @InjectView(R.id.imageButtonLocalSettings)
    ImageButton imageButtonLocalSettings;
    @InjectView(R.id.editTextSetAddress)
    EditText editTextSetAddress;
    @InjectView(R.id.buttonSetAddress)
    ImageButton buttonSetAddress;
    private HashMap<TAGS, ArrayList<String>> resolveData;

    private String infoTypeSaved = "";
    private TextToSpeech textToSpeech;
    private boolean textToSpeechInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            textToSpeech.stop();
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseTextToSpeech();
    }

    private void releaseTextToSpeech() {
        try {
            textToSpeechInit = false;
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            textToSpeech.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextSetAddress.setText(Prefs.getString(Constants.KEY_CADE_URL, Constants.LOCAL_CADE_URL));
    }

    private void init() {
        initViews();
        setListeners();


        initResolveData();

        textToSpeechInit = false;
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechInit = true;
                } else {
                    textToSpeechInit = false;
                }
            }
        });
    }

    private void initResolveData() {
        resolveData = new HashMap<>();
        ArrayList<String> temp;
        List<String> data;
        for (TAGS tag : TAGS.values()) {
            data = getResolveData(tag);
            temp = resolveData.get(tag);
            if (temp == null) {
                resolveData.put(tag, new ArrayList<String>());
                temp = resolveData.get(tag);
            }
            temp.addAll(data);
            if (temp.isEmpty()) {
                temp.add(getString(R.string.no_data_available));
            }
            resolveData.put(tag, temp);
        }
    }

    private List<String> getResolveData(TAGS tag) {
        List<String> data = new ArrayList<String>();
        if (tag != null) {
            String item = "";
            if (tag == TAGS.cinema) {
                data.add(getString(R.string.cinema_1));
            } else if (tag == TAGS.culture) {
                data.add(getString(R.string.culture_1));
                data.add(getString(R.string.culture_2));
                data.add(getString(R.string.culture_3));
                data.add(getString(R.string.culture_4));
            } else if (tag == TAGS.curiosities) {
                data.add(getString(R.string.curiosities_1));
                data.add(getString(R.string.curiosities_2));
                data.add(getString(R.string.curiosities_3));
                data.add(getString(R.string.curiosities_4));
                data.add(getString(R.string.curiosities_5));
                data.add(getString(R.string.curiosities_6));
                data.add(getString(R.string.curiosities_7));
                data.add(getString(R.string.curiosities_8));
                data.add(getString(R.string.curiosities_9));
                data.add(getString(R.string.curiosities_10));
                data.add(getString(R.string.curiosities_11));
                data.add(getString(R.string.curiosities_12));
                data.add(getString(R.string.curiosities_13));
                data.add(getString(R.string.curiosities_14));
                data.add(getString(R.string.curiosities_15));
                data.add(getString(R.string.curiosities_16));
                data.add(getString(R.string.curiosities_17));
            } else if (tag == TAGS.exhibitions) {
                data.add(getString(R.string.exhibitions_1));
                data.add(getString(R.string.exhibitions_2));
            } else if (tag == TAGS.sports) {
                data.add(getString(R.string.sports_1));
                data.add(getString(R.string.sports_2));
            } else if (tag == TAGS.world) {
                data.add(getString(R.string.world_1));
                data.add(getString(R.string.world_2));
                data.add(getString(R.string.world_3));
                data.add(getString(R.string.world_4));
                data.add(getString(R.string.world_5));
                data.add(getString(R.string.world_6));
                data.add(getString(R.string.world_7));
                data.add(getString(R.string.world_8));
                data.add(getString(R.string.world_9));
                data.add(getString(R.string.world_10));
                data.add(getString(R.string.world_11));
                data.add(getString(R.string.world_12));
                data.add(getString(R.string.world_13));
                data.add(getString(R.string.world_14));
                data.add(getString(R.string.world_15));
                data.add(getString(R.string.world_16));
                data.add(getString(R.string.world_17));
            }
        }
        return data;
    }

    private String getResolveDataString(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            for (TAGS t : TAGS.values()) {
                if (TextUtils.equals(t.toString().toLowerCase(), tag.toLowerCase())) {
                    return getResolveDataString(t);
                }
            }
        }
        return getString(R.string.no_data_available);
    }

    private String getResolveDataString(TAGS tag) {
        if (tag != null) {
            List<String> dataList = resolveData.get(tag);
            if (dataList != null && !dataList.isEmpty()) {
                Random random = new Random();
                return dataList.get(random.nextInt(dataList.size()));
            }
        }
        return getString(R.string.no_data_available);
    }

    private void initViews() {
        initActionBar();
        circleButton = (CircleButton) findViewById(R.id.voiceControlBtn);
        backToPAButton = (BackToPAButton) findViewById(R.id.backControlBtn);
        editTextSetAddress.setText(Prefs.getString(Constants.KEY_CADE_URL, Constants.LOCAL_CADE_URL));
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void setListeners() {
        circleButton.setOnTouchListener(new MicrophoneTouchListener());
        backToPAButton.setOnTouchListener(new BackTouchListener());
        imageButtonLocalSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSetAddress.getVisibility() == View.GONE) {
                    editTextSetAddress.setVisibility(View.VISIBLE);
                    buttonSetAddress.setVisibility(View.VISIBLE);
                } else {
                    editTextSetAddress.setVisibility(View.GONE);
                    buttonSetAddress.setVisibility(View.GONE);
                }
            }
        });
        editTextSetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Prefs.setString(Constants.KEY_CADE_URL, editTextSetAddress.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        buttonSetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cade.SetCadeBackendUrl(editTextSetAddress.getText().toString());
            }
        });
    }

    @Override
    public void performAction(String command, Map<String, String> map) {
        Log.d(TAG, "performAction command: #" + command + "#, map: #" + StringUtils.getReadableString(map) + "#");
        if (TextUtils.equals(command, Constants.CADE_ACTION_GET_INFO)) {
            String nType = map.get(Constants.CADE_WHQUERY_SELECTED_INFO_TYPE);
            if (!TextUtils.isEmpty(nType)) {
                infoTypeSaved = nType;
            } else {
                infoTypeSaved = "";
            }
            String textToRead = getResolveDataString(infoTypeSaved);
            speak(textToRead);
        }
        cade.sendActionResult(true);
    }
    @Override
    public void performWhQuery(String command, Map<String, String> map) {
    }

    @Override
    public void performValidity(String command, Map<String, String> map) {
    }

    @Override
    public void performEntityRecognizer(String command, Map<String, String> map) {
    }

    private void speak(@NonNull String textToRead) {
        Log.d(TAG, "textToRead " + textToRead);
        try {
            if (textToSpeech != null && !TextUtils.isEmpty(textToRead) && textToSpeechInit) {
                if (textToSpeech.isSpeaking()) {
                    textToSpeech.stop();
                }
                textToSpeech.setLanguage(Locale.getDefault());
                if (textToRead.length() > TextToSpeech.getMaxSpeechInputLength()) {
                    textToRead = textToRead.substring(0, TextToSpeech.getMaxSpeechInputLength() - 1);
                }
                textToSpeech.setSpeechRate(0.95f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, "" + System.currentTimeMillis());
                } else {
                    textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public enum TAGS {
        culture,
        sports,
        exhibitions,
        world,
        cinema,
        curiosities
    }
}
