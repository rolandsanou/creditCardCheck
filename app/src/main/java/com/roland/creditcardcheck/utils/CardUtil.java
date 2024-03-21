package com.roland.creditcardcheck.utils;

import java.util.ArrayList;
import java.util.List;

public class CardUtil {
    private static final String LOG_TAG = CardUtil.class.getSimpleName();
    private static final List<CardType> SUPPORTED_CARDS = getAllCardTypes();
    private static boolean isLoggingEnabled = true;
    private static int cardType = 0;

    private static List<CardType> getAllCardTypes() {
        List<CardType> cardTypes = new ArrayList<>();
        cardTypes.add(CardType.Visa);
        cardTypes.add(CardType.MasterCard);
        cardTypes.add(CardType.AmericanExpress);
        cardTypes.add(CardType.DinersClub);
        cardTypes.add(CardType.Discover);
        cardTypes.add(CardType.Jcb);
        return cardTypes;
    }

    /*
     * Reference link: https://www.geeksforgeeks.org/luhn-algorithm/
     * This method checks the validity of a given raw card number via Luhn algorithm/modulus 10
     */
    private static boolean isValidCardNumberByLuhn(String stringInputCardNumber) {
        int sum = 0;
        boolean isSecondDigit = false;

        for (int i = stringInputCardNumber.length() - 1; i >= 0; i--) {
            int d = stringInputCardNumber.charAt(i) - '0';

            if (isSecondDigit) {
                d = d * 2;
            }

            sum += d / 10;
            sum += d % 10;

            isSecondDigit = !isSecondDigit;
        }

        boolean result = (sum % 10) == 0;
        printLog("isValidCardNumber By Luhn () = " + result);
        return result;
    }

    private static boolean isValidCardNumberByTypeSupport(String stringInputCardNumber) {
        for (CardType supportedType : SUPPORTED_CARDS) {
            if (supportedType.getPattern().matcher(stringInputCardNumber).matches()) {
                cardType = supportedType.getCardType();
                printLog("isValidCardNumber By Type Support() = true");
                return true;
            }
        }

        printLog("isValidCardNumber By Type Support () = false");
        return false;
    }

    private static boolean isValidCardNumberLength(String inputCardNumber) {
        boolean result = inputCardNumber.length() >= CardType.CARD_NUMBER_LENGTH_MIN &&
                inputCardNumber.length() <= CardType.CARD_NUMBER_LENGTH_MAX;

        printLog("isValidCardNumber Length () = " + result);
        return result;
    }

    private static boolean isValidCardNumberValue(long inputCardNumber) {
        return inputCardNumber > 0;
    }

    private static void printLog(String logMessage) {
        if (isLoggingEnabled) {
            System.out.println(LOG_TAG + "." + logMessage);
        }
    }

    private static void setLoggingEnabled(boolean isEnabled) {
        isLoggingEnabled = isEnabled;
    }

    public void start(boolean start){
        if(start){
            setUp(true, getAllCardTypes());
        }
    }

    private static void setSupportedCardTypes(List<CardType> listSupportedCardTypes) {
        if (listSupportedCardTypes.isEmpty()) {
            printLog("setSupportedCardTypes() : Cannot set empty selection of CardTypes.");
        } else {
            SUPPORTED_CARDS.clear();
            SUPPORTED_CARDS.addAll(listSupportedCardTypes);
        }
    }

    public static void setUp(boolean isLoggingEnabled, List<CardType> listSupportedCardTypes) {
        setLoggingEnabled(isLoggingEnabled);
        setSupportedCardTypes(listSupportedCardTypes);
    }

    public boolean isValidCard(long inputCardNumber, int inputCVC,
                                      int inputExpMonth, int inputExpYear) {
        return isValidCardNumber(inputCardNumber) && isValidCVC(inputCVC) &&
                isValidExpirationDate(inputExpMonth, inputExpYear);
    }

    /*
     * @param inputCardNumber : the 12-19 digit number
     * */
    public static boolean isValidCardNumber(long inputCardNumber) {
        printLog("isValidCardNumber() : input = " + inputCardNumber);
        return isValidCardNumberValue(inputCardNumber) &&
                isValidCardNumberLength(Long.toString(inputCardNumber)) &&
                isValidCardNumberByTypeSupport(Long.toString(inputCardNumber)) &&
                isValidCardNumberByLuhn(Long.toString(inputCardNumber));
    }

    public int cardType(){
        return cardType;
    }

    /*
     * @param inputCVC : 3-4 digit CVC/CVV value
     * */
    public static boolean isValidCVC(int inputCVC) {
        String stringInputCVC = Integer.toString(inputCVC);
        boolean result = stringInputCVC.length() >= CardType.CVC_LENGTH_MIN &&
                stringInputCVC.length() <= CardType.CVC_LENGTH_MAX;
        printLog("isValidCVC() : " + stringInputCVC + " = " + result);

        return result;
    }

    /*
     * @param inputExpMonth : non-zero based month value. Range of 1-12
     * @param inputExpYear : 4-digit year value
     * */
    public static boolean isValidExpirationDate(int inputExpMonth, int inputExpYear) {
        boolean isValidMonthRange =
                inputExpMonth >= CardType.CARD_EXP_MONTH_MIN &&
                        inputExpMonth <= CardType.CARD_EXP_MONTH_MAX;
        boolean isValidYearValue = inputExpYear > 0;
        boolean isValidYearLength = Integer.toString(inputExpYear).length() ==
                CardType.CARD_EXP_YEAR_LENGTH;

        boolean isFutureYear = inputExpYear > CardType.CURRENT_YEAR;
        boolean isSameYear_FutureOrCurrentMonth =
                inputExpYear == CardType.CURRENT_YEAR && inputExpMonth >= CardType.CURRENT_MONTH;

        boolean result = (isValidMonthRange && isValidYearLength && isValidYearValue) &&
                (isFutureYear || isSameYear_FutureOrCurrentMonth);

        printLog("isValidExpirationDate() : " + inputExpMonth + " / " + inputExpYear + " = " + result);
        return result;
    }
}
