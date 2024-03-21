package com.roland.creditcardcheck;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.roland.creditcardcheck.utils.CardUtil;
import com.roland.creditcardcheck.utils.cardTypeEnum;

public class MainActivity extends AppCompatActivity {
    private TextView _cardOwnerName, _cardNumber, _cardExp;
    private ImageView _cardType;
    private CardView _cardFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_main);

        TextInputLayout nameTextInLayout = findViewById(R.id.name);
        TextInputEditText textInputEditTextName = findViewById(R.id.nameEdit);

        TextInputLayout numTextInLayout = findViewById(R.id.cardnum);
        TextInputEditText textInputEditTextNum = findViewById(R.id.cardnumEdit);

        TextInputLayout ccvTextInLayout = findViewById(R.id.card_ccv);
        TextInputEditText textInputEditTextCvv = findViewById(R.id.card_ccvEdit);

        TextInputLayout dateTextInLayout = findViewById(R.id.card_date);
        TextInputEditText textInputEditTextDate = findViewById(R.id.card_dateEdit);

        Button checkButton = findViewById(R.id.button_to_check);
        _cardType = findViewById(R.id.cardType);
        _cardFull = findViewById(R.id.card);
        LinearLayout face = findViewById(R.id.face);
        LinearLayout back = findViewById(R.id.back);

        CardUtil cardUtil = new CardUtil();
        cardUtil.start(true);
        checkButton.setOnClickListener(v -> {
            long cardNum = Long.parseLong(textInputEditTextNum.getEditableText().toString());
            int cvc = Integer.parseInt(textInputEditTextCvv.getEditableText().toString());
            String[] date = textInputEditTextDate.getEditableText().toString().split("/");
            int month = Integer.parseInt(date[0]);
            int year = 2000 + Integer.parseInt(date[1]);
            if(cardNum > 0  && cvc > 0
                    && month > 0 && year > 2000){
                Boolean checkValue = cardUtil.isValidCard(cardNum, cvc, month, year);
                typeControl(cardUtil.cardType());
                Toast.makeText(this, "This card valid: "+checkValue, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Empty input", Toast.LENGTH_SHORT).show();
            }
        });

        _cardNumber = findViewById(R.id.cardNumber);
        _cardExp = findViewById(R.id.cardExp);
        _cardOwnerName= findViewById(R.id.cardOwnerName);

        inputCheckName(nameTextInLayout, textInputEditTextName);
        inputCheckDate(dateTextInLayout, textInputEditTextDate);
        inputCheckNum(numTextInLayout, textInputEditTextNum);

        _cardFull.setOnClickListener(v -> {
            back.getVisibility();
            face.setVisibility(face.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            back.setVisibility(back.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });

        transitionAnimation(_cardFull);
    }

    private void typeControl(int type){
        switch (type){
            case cardTypeEnum.Visa:
                _cardType.setImageResource(R.drawable.visa);
                break;
            case cardTypeEnum.AmericanExpress:
                _cardType.setImageResource(R.drawable.american);
                break;
            case cardTypeEnum.DinersClub:
                _cardType.setImageResource(R.drawable.diners);
                break;
            case cardTypeEnum.MasterCard:
                _cardType.setImageResource(R.drawable.mc_symbol);
                break;
            case cardTypeEnum.Discover:
                _cardType.setImageResource(R.drawable.discover);
                break;
            default:
                break;
        }
    }

    private void transitionAnimation(CardView card){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(500);
        card.startAnimation(alphaAnimation);
    }

    private void inputCheckName(TextInputLayout layInput, TextInputEditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _cardOwnerName.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void inputCheckNum(TextInputLayout layInput, TextInputEditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _cardNumber.setText(addSpaces(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                _cardNumber.setText(addSpaces(s.toString()));
            }
        });
    }

    public static String addSpaces(String number) {
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (int i = 0; i < number.length(); i++) {
            result.append(number.charAt(i));
            count++;

            if (count == 4 && i != number.length() - 1) {
                result.append(' ');
                count = 0;
            }
        }

        return result.toString();
    }

    private void inputCheckCvv(TextInputLayout layInput, TextInputEditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void inputCheckDate(TextInputLayout layInput, TextInputEditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _cardExp.setText("Exp date:"+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}