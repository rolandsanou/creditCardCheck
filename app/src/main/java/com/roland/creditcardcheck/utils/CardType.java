package com.roland.creditcardcheck.utils;

import java.util.Calendar;
import java.util.regex.Pattern;
import com.roland.creditcardcheck.utils.cardTypeEnum;

public class CardType {
    private final Pattern pattern;
    private int cardType;

    private CardType(String pattern, int cardType) {
        this.pattern = Pattern.compile(pattern);
        this.cardType = cardType;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getCardType(){
        return cardType;
    }

    public static final CardType Visa = new CardType("^4[0-9]{6,}$", cardTypeEnum.Visa) {};
    public static final CardType MasterCard = new CardType("^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$", cardTypeEnum.MasterCard) {};
    public static final CardType AmericanExpress = new CardType("^3[47][0-9]{5,}$", cardTypeEnum.AmericanExpress) {};
    public static final CardType DinersClub = new CardType("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$", cardTypeEnum.DinersClub) {};
    public static final CardType Discover = new CardType("^6(?:011|5[0-9]{2})[0-9]{3,}$", cardTypeEnum.Discover) {};
    public static final CardType Jcb = new CardType("^(?:2131|1800|35[0-9]{3})[0-9]{3,}$", cardTypeEnum.Jcb) {};

    public static final int CVC_LENGTH_MAX = 4;
    public static final int CVC_LENGTH_MIN = 3;

    public static final int CARD_NUMBER_LENGTH_MAX = 19;
    public static final int CARD_NUMBER_LENGTH_MIN = 12;

    public static final int CARD_EXP_MONTH_MAX = 12;
    public static final int CARD_EXP_MONTH_MIN = 1;

    public static final int CARD_EXP_YEAR_LENGTH = 4;

    public static final int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
    public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
}
