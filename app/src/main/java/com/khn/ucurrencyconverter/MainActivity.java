package com.khn.ucurrencyconverter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AdView banner;
    AdRequest adRequest;
    InterstitialAd interstitialAd;
    int DayBlue, NightBlue, DayGreen, NightGreen, DayTeal, NightTeal, DayIndigo, NightIndigo, DayPink, NightPink;
    String from, to, amount, result;
    Spinner spFrom, spTo;
    ProgressDialog progressDoalog;
    ImageView iv;

    EditText edInput;
    TextView tvResult;
    Button btnConvert;
    int themeClr;
    boolean DayNight;
    SharedPreferences share;
    SharedPreferences.Editor editor;
    ImageView ivCp;
    String[] tFrom = {"USD", "MMK", "THB", "MYR", "JPY", "KRW", "SGD", "IDR", "BTC", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CHN", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DEM", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FIM", "FJD", "FKP", "FRF", "GBP", "GEL", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IEP", "ILS", "INR", "IQD", "IRR", "ISK", "ITL", "JMD", "JOD", "KES", "KGS", "KHR", "KMF", "KPW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SHP", "SKK", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "UYU", "UZS", "UEF", "VND", "VUV", "WST", "XAF", "XCD", "XDR", "CFA", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL"};
    String[] tTo = {"MMK", "USD", "THB", "MYR", "JPY", "KRW", "SGD", "IDR", "BTC", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CHN", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DEM", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FIM", "FJD", "FKP", "FRF", "GBP", "GEL", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IEP", "ILS", "INR", "IQD", "IRR", "ISK", "ITL", "JMD", "JOD", "KES", "KGS", "KHR", "KMF", "KPW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SHP", "SKK", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "UYU", "UZS", "UEF", "VND", "VUV", "WST", "XAF", "XCD", "XDR", "CFA", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DayBlue = R.style.DayBlue;
        NightBlue = R.style.NightBlue;
        DayGreen = R.style.DayGreen;
        NightGreen = R.style.NightGreen;
        DayTeal = R.style.DayTeal;
        NightTeal = R.style.NightTeal;
        DayIndigo = R.style.DayIndigo;
        NightIndigo = R.style.NightIndigo;
        DayPink = R.style.DayPink;
        NightPink = R.style.NightPink;

        checkTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.bar);

        from = "USD";
        to = "MMK";
        amount = "1";

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading...");
        progressDoalog.setTitle("Please Wait :)");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        spinTo();
        spinFrom();

        edInput = (EditText) findViewById(R.id.inPut);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnConvert = (Button) findViewById(R.id.btnConvert);

        btnConvert.setOnClickListener(this);
        chageBG(themeClr);

        ivCp = (ImageView) findViewById(R.id.ivCp);
        ivCp.setVisibility(View.GONE);

        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlankFragment ll = new BlankFragment();
                ll.show(getSupportFragmentManager(), "Hellop");
            }
        });

        adRequest = new AdRequest.Builder().build();
        banner = (AdView) findViewById(R.id.adView);
        banner.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1325188641119577/3386216481");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAD();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loadAD();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                loadAD();
            }
        });
    }

    @Override
    public void onClick(View view) {
        showAD();
        amount = edInput.getText().toString();
        if (!amount.isEmpty()) {
            if (isInternetOn() == true) {
                http://app.htetznaing.com/currency-converter/index.php?a=100&from=USD&to=MMK
                new showHTML().execute("http://app.htetznaing.com/currency-converter/index.php?a="+amount+"&from="+from+"&to="+to);
                progressDoalog.show();
                ivCp.setVisibility(View.GONE);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ATTENTION Please :(");
                builder.setMessage("If you want to know current time exchange rates you need to Internet Access in Your Phone!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showAD();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } else {
            Toast.makeText(this, "Please fill Amount!", Toast.LENGTH_SHORT).show();
        }
    }

    public void copy(View view) {
        final ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        final String ll = tvResult.getText().toString();
        if (!ll.isEmpty() || !ll.equals(null)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose!");
            builder.setMessage(ll);
            builder.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    copy.setText(ll);
                    if (copy.hasText() == true) {
                        Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent in = new Intent(Intent.ACTION_SEND);
                    in.putExtra(Intent.EXTRA_TEXT, ll);
                    in.setType("text/plain");
                    startActivity(new Intent(Intent.createChooser(in, "Send App Via...")));
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void dev(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("fb://profile/100011339710114"));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://m.facebook.com/KHtetzNaing"));
            startActivity(intent);
        }
    }

    public void shareThis(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "ေငြေဈးႏႈန္းမ်ားအခ်ိန္နဲ႔ တေျပးညီသိႏိုင္ဖို႔ uCurrency Converter\n" +
                "တစ္ကမာၻလုံးကေငြေၾကးနဲ႔ Bitcoin ေဈးႏႈန္မ်ားကိုပါ\n" +
                "အလြယ္တကူ Convert လုပ္ေပးႏိုင္ပါတယ္။\n" +
                "Download Free at Google Play Store : https://play.google.com/store/apps/details?id=com.htetznaing.ucurrencyconverter");
        startActivity(new Intent(Intent.createChooser(intent, "Share App Via...")));
    }

    public void spinFrom() {
        ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("US Dollar ($)", R.drawable.usd));
        list.add(new ItemData("Myanmar Kyat (MMK)", R.drawable.mmk));
        list.add(new ItemData("Thai Baht (THB)", R.drawable.thb));
        list.add(new ItemData("Malaysian Ringgit (MYR)", R.drawable.myr));
        list.add(new ItemData("Japanese Yen (¥)", R.drawable.jpy));
        list.add(new ItemData("South Korean Won (₩)", R.drawable.krw));
        list.add(new ItemData("Singapore Dollar (SGD)", R.drawable.sgd));
        list.add(new ItemData("Indonesian Rupiah (IDR)", R.drawable.idr));
        list.add(new ItemData("Bitcoin (฿)", R.drawable.btc));

        list.add(new ItemData("United Arab Emirates Dirham (AED)", R.drawable.aed));
        list.add(new ItemData("Afghan Afghani (AFN)", R.drawable.afn));
        list.add(new ItemData("Albanian Lek (ALL)", R.drawable.all));
        list.add(new ItemData("Armenian Dram (AMD)", R.drawable.amd));
        list.add(new ItemData("Netherlands Antillean Guilder (ANG)", R.drawable.ang));
        list.add(new ItemData("Angolan Kwanza (AOA)", R.drawable.aoa));
        list.add(new ItemData("Argentine Peso (ARS)", R.drawable.ars));
        list.add(new ItemData("Australian Dollar (A$)", R.drawable.aud));
        list.add(new ItemData("Aruban Florin (AWG)", R.drawable.awg));
        list.add(new ItemData("Azerbaijani Manat (AZN)", R.drawable.azn));
        list.add(new ItemData("Bosnia-Herzegovina Convertible Mark (BAM)", R.drawable.bam));
        list.add(new ItemData("Barbadian Dollar (BBD)", R.drawable.bbd));
        list.add(new ItemData("Bangladeshi Taka (BDT)", R.drawable.bdt));
        list.add(new ItemData("Bulgarian Lev (BGN)", R.drawable.bgn));
        list.add(new ItemData("Bahraini Dinar (BHD)", R.drawable.bhd));
        list.add(new ItemData("Burundian Franc (BIF)", R.drawable.bif));
        list.add(new ItemData("Bermudan Dollar (BMD)", R.drawable.bmd));
        list.add(new ItemData("Brunei Dollar (BND)", R.drawable.bnd));
        list.add(new ItemData("Bolivian Boliviano (BOB)", R.drawable.bob));
        list.add(new ItemData("Brazilian Real (R$)", R.drawable.brl));
        list.add(new ItemData("Bahamian Dollar (BSD)", R.drawable.bsd));
        list.add(new ItemData("Bhutanese Ngultrum (BTN)", R.drawable.btn));
        list.add(new ItemData("Botswanan Pula (BWP)", R.drawable.bwp));
        list.add(new ItemData("Belarusian Ruble (BYN)", R.drawable.byn));
        list.add(new ItemData("Belarusian Ruble (2000–2016) (BYR)", R.drawable.byr));
        list.add(new ItemData("Belize Dollar (BZD)", R.drawable.bzd));
        list.add(new ItemData("Canadian Dollar (CA$)", R.drawable.cad));
        list.add(new ItemData("Congolese Franc (CDF)", R.drawable.cdf));
        list.add(new ItemData("Swiss Franc (CHF)", R.drawable.chf));

        list.add(new ItemData("Chilean Unit of Account (UF) (CLF)", R.drawable.clf));
        list.add(new ItemData("Chilean Peso (CLP)", R.drawable.clp));
        list.add(new ItemData("CHN (CHN)", R.drawable.chn));
        list.add(new ItemData("Chinese Yuan (CN¥)", R.drawable.cny));
        list.add(new ItemData("Colombian Peso (COP)", R.drawable.cop));
        list.add(new ItemData("Costa Rican Colón (CRC)", R.drawable.crc));
        list.add(new ItemData("Cuban Peso (CUP)", R.drawable.cup));
        list.add(new ItemData("Cape Verdean Escudo (CVE)", R.drawable.cve));
        list.add(new ItemData("Czech Koruna (CZK)", R.drawable.czk));
        list.add(new ItemData("German Mark (DEM)", R.drawable.dem));
        list.add(new ItemData("Djiboutian Franc (DJF)", R.drawable.djf));
        list.add(new ItemData("Danish Krone (DKK)", R.drawable.dkk));
        list.add(new ItemData("Dominican Peso (DOP)", R.drawable.dop));
        list.add(new ItemData("Algerian Dinar (DZD)", R.drawable.dzd));
        list.add(new ItemData("Egyptian Pound (EGP)", R.drawable.egp));
        list.add(new ItemData("Eritrean Nakfa (ERN)", R.drawable.ern));
        list.add(new ItemData("Ethiopian Birr (ETB)", R.drawable.etb));
        list.add(new ItemData("Euro (€)", R.drawable.eur));
        list.add(new ItemData("Finnish Markka (FIM)", R.drawable.fim));
        list.add(new ItemData("Fijian Dollar (FJD)", R.drawable.fjd));
        list.add(new ItemData("Falkland Islands Pound (FKP)", R.drawable.fkp));
        list.add(new ItemData("French Franc (FRF)", R.drawable.frf));
        list.add(new ItemData("British Pound (£)", R.drawable.gbp));
        list.add(new ItemData("Georgian Lari (GEL)", R.drawable.gel));
        list.add(new ItemData("Ghanaian Cedi (GHS)", R.drawable.ghs));
        list.add(new ItemData("Gibraltar Pound (GIP)", R.drawable.gip));
        list.add(new ItemData("Gambian Dalasi (GMD)", R.drawable.gmd));
        list.add(new ItemData("Guinean Franc (GNF)", R.drawable.gnf));
        list.add(new ItemData("Guatemalan Quetzal (GTQ)", R.drawable.gtq));
        list.add(new ItemData("Guyanaese Dollar (GYD)", R.drawable.gyd));

        list.add(new ItemData("Hong Kong Dollar (HK$)", R.drawable.hkd));
        list.add(new ItemData("Honduran Lempira (HNL)", R.drawable.hnl));
        list.add(new ItemData("Croatian Kuna (HRK)", R.drawable.hrk));
        list.add(new ItemData("Haitian Gourde (HTG)", R.drawable.htg));
        list.add(new ItemData("Hungarian Forint (HUF)", R.drawable.huf));
        list.add(new ItemData("Irish Pound (IEP)", R.drawable.iep));
        list.add(new ItemData("Israeli New Shekel (₪)", R.drawable.ils));
        list.add(new ItemData("Indian Rupee (₹)", R.drawable.inr));
        list.add(new ItemData("Iraqi Dinar (IQD)", R.drawable.iqd));
        list.add(new ItemData("Iranian Rial (IRR)", R.drawable.irr));
        list.add(new ItemData("Icelandic Króna (ISK)", R.drawable.isk));
        list.add(new ItemData("Italian Lira (ITL)", R.drawable.itl));
        list.add(new ItemData("Jamaican Dollar (JMD)", R.drawable.jmd));
        list.add(new ItemData("Jordanian Dinar (JOD)", R.drawable.jod));
        list.add(new ItemData("Kenyan Shilling (KES)", R.drawable.kes));
        list.add(new ItemData("Kyrgystani Som (KGS)", R.drawable.kgs));
        list.add(new ItemData("Cambodian Riel (KHR)", R.drawable.khr));
        list.add(new ItemData("Comorian Franc (KMF)", R.drawable.kmf));
        list.add(new ItemData("North Korean Won (KPW)", R.drawable.kpw));
        list.add(new ItemData("Kuwaiti Dinar (KWD)", R.drawable.kwd));
        list.add(new ItemData("Cayman Islands Dollar (KYD)", R.drawable.kyd));
        list.add(new ItemData("Kazakhstani Tenge (KZT)", R.drawable.kzt));
        list.add(new ItemData("Laotian Kip (LAK)", R.drawable.lak));
        list.add(new ItemData("Lebanese Pound (LBP)", R.drawable.lbp));
        list.add(new ItemData("Sri Lankan Rupee (LKR)", R.drawable.lkr));
        list.add(new ItemData("Liberian Dollar (LRD)", R.drawable.lrd));
        list.add(new ItemData("Lesotho Loti (LSL)", R.drawable.lsl));
        list.add(new ItemData("Lithuanian Litas (LTL)", R.drawable.ltl));

        list.add(new ItemData("Latvian Lats (LVL)", R.drawable.lvl));
        list.add(new ItemData("Libyan Dinar (LYD)", R.drawable.lyd));
        list.add(new ItemData("Moroccan Dirham (MAD)", R.drawable.mad));
        list.add(new ItemData("Moldovan Leu (MDL)", R.drawable.mdl));
        list.add(new ItemData("Malagasy Ariary (MGA)", R.drawable.mga));
        list.add(new ItemData("Macedonian Denar (MKD)", R.drawable.mkd));
        list.add(new ItemData("Mongolian Tugrik (MNT)", R.drawable.mnt));
        list.add(new ItemData("Macanese Pataca (MOP)", R.drawable.mop));
        list.add(new ItemData("Mauritanian Ouguiya (MRO)", R.drawable.mro));
        list.add(new ItemData("Mauritian Rupee (MUR)", R.drawable.mur));
        list.add(new ItemData("Maldivian Rufiyaa (MVR)", R.drawable.mvr));
        list.add(new ItemData("Malawian Kwacha (MWK)", R.drawable.mwk));
        list.add(new ItemData("Mexican Peso (MX$)", R.drawable.mxn));
        list.add(new ItemData("Mozambican Metical (MZN)", R.drawable.mzn));
        list.add(new ItemData("Namibian Dollar (NAD)", R.drawable.nad));
        list.add(new ItemData("Nigerian Naira (NGN)", R.drawable.ngn));
        list.add(new ItemData("Nicaraguan Córdoba (NIO)", R.drawable.nio));
        list.add(new ItemData("Norwegian Krone (NOK)", R.drawable.nok));
        list.add(new ItemData("Nepalese Rupee (NPR)", R.drawable.npr));
        list.add(new ItemData("New Zealand Dollar (NZ$)", R.drawable.nzd));
        list.add(new ItemData("Omani Rial (OMR)", R.drawable.omr));
        list.add(new ItemData("Panamanian Balboa (PAB)", R.drawable.pab));
        list.add(new ItemData("Peruvian Sol (PEN)", R.drawable.pen));
        list.add(new ItemData("Papua New Guinean Kina (PGK)", R.drawable.pgk));
        list.add(new ItemData("Philippine Peso (PHP)", R.drawable.php));
        list.add(new ItemData("Pakistani Rupee (PKR)", R.drawable.pkr));

        list.add(new ItemData("Polish Zloty (PLN)", R.drawable.pln));
        list.add(new ItemData("Paraguayan Guarani (PYG)", R.drawable.pyg));
        list.add(new ItemData("Qatari Rial (QAR)", R.drawable.qar));
        list.add(new ItemData("Romanian Leu (RON)", R.drawable.ron));
        list.add(new ItemData("Serbian Dinar (RSD)", R.drawable.rsd));
        list.add(new ItemData("Russian Ruble (RUB)", R.drawable.rub));
        list.add(new ItemData("Rwandan Franc (RWF)", R.drawable.rwf));
        list.add(new ItemData("Saudi Riyal (SAR)", R.drawable.sar));
        list.add(new ItemData("Solomon Islands Dollar (SBD)", R.drawable.sbd));
        list.add(new ItemData("Seychellois Rupee (SCR)", R.drawable.scr));
        list.add(new ItemData("Sudanese Pound (SDG)", R.drawable.sdg));
        list.add(new ItemData("Swedish Krona (SEK)", R.drawable.sek));
        list.add(new ItemData("St. Helena Pound (SHP)", R.drawable.shp));
        list.add(new ItemData("Slovak Koruna (SKK)", R.drawable.skk));
        list.add(new ItemData("Sierra Leonean Leone (SLL)", R.drawable.sll));
        list.add(new ItemData("Somali Shilling (SOS)", R.drawable.sos));
        list.add(new ItemData("Surinamese Dollar (SRD)", R.drawable.srd));
        list.add(new ItemData("São Tomé & Príncipe Dobra (STD)", R.drawable.std));
        list.add(new ItemData("Salvadoran Colón (SVC)", R.drawable.svc));
        list.add(new ItemData("Syrian Pound (SYP)", R.drawable.syp));
        list.add(new ItemData("Swazi Lilangeni (SZL)", R.drawable.szl));
        list.add(new ItemData("Tajikistani Somoni (TJS)", R.drawable.tjs));
        list.add(new ItemData("Turkmenistani Manat (TMT)", R.drawable.tmt));
        list.add(new ItemData("Tunisian Dinar (TND)", R.drawable.tnd));
        list.add(new ItemData("Tongan Paʻanga (TOP)", R.drawable.top));
        list.add(new ItemData("Turkish Lira (TRY)", R.drawable.tryy));
        list.add(new ItemData("Trinidad & Tobago Dollar (TTD)", R.drawable.ttd));
        list.add(new ItemData("New Taiwan Dollar (NT$)", R.drawable.twd));
        list.add(new ItemData("Tanzanian Shilling (TZS)", R.drawable.tzs));
        list.add(new ItemData("Ukrainian Hryvnia (UAH)", R.drawable.uah));

        list.add(new ItemData("Ugandan Shilling (UGX)", R.drawable.ugx));
        list.add(new ItemData("Uruguayan Peso (UYU)", R.drawable.uyu));
        list.add(new ItemData("Uzbekistani Som (UZS)", R.drawable.uzs));
        list.add(new ItemData("Venezuelan Bolívar (VEF)", R.drawable.vef));
        list.add(new ItemData("Vietnamese Dong (₫)", R.drawable.vnd));
        list.add(new ItemData("Vanuatu Vatu (VUV)", R.drawable.vuv));
        list.add(new ItemData("Samoan Tala (WST)", R.drawable.wst));
        list.add(new ItemData("Central African CFA Franc (FCFA)", R.drawable.xaf));
        list.add(new ItemData("East Caribbean Dollar (EC$)", R.drawable.xcd));
        list.add(new ItemData("Special Drawing Rights (XDR)", R.drawable.xdr));
        list.add(new ItemData("West African CFA Franc (CFA)", R.drawable.cfa));
        list.add(new ItemData("CFP Franc (CFPF)", R.drawable.xpf));
        list.add(new ItemData("Yemeni Rial (YER)", R.drawable.yer));
        list.add(new ItemData("South African Rand (ZAR)", R.drawable.zar));
        list.add(new ItemData("Zambian Kwacha (1968–2012) (ZMK)", R.drawable.zmk));
        list.add(new ItemData("Zambian Kwacha (ZMW)", R.drawable.zmw));
        list.add(new ItemData("Zimbabwean Dollar (2009) (ZWL)", R.drawable.zwl));


        spFrom = (Spinner) findViewById(R.id.spinnerFrom);
        SpinnerAdapter adapter = new SpinnerAdapter(this,
                R.layout.spinner_item, R.id.tv, list);
        spFrom.setAdapter(adapter);
        spFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                from = tFrom[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void spinTo() {
        ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("Myanmar Kyat (MMK)", R.drawable.mmk));
        list.add(new ItemData("US Dollar ($)", R.drawable.usd));
        list.add(new ItemData("Thai Baht (THB)", R.drawable.thb));
        list.add(new ItemData("Malaysian Ringgit (MYR)", R.drawable.myr));
        list.add(new ItemData("Japanese Yen (¥)", R.drawable.jpy));
        list.add(new ItemData("South Korean Won (₩)", R.drawable.krw));
        list.add(new ItemData("Singapore Dollar (SGD)", R.drawable.sgd));
        list.add(new ItemData("Indonesian Rupiah (IDR)", R.drawable.idr));
        list.add(new ItemData("Bitcoin (฿)", R.drawable.btc));

        list.add(new ItemData("United Arab Emirates Dirham (AED)", R.drawable.aed));
        list.add(new ItemData("Afghan Afghani (AFN)", R.drawable.afn));
        list.add(new ItemData("Albanian Lek (ALL)", R.drawable.all));
        list.add(new ItemData("Armenian Dram (AMD)", R.drawable.amd));
        list.add(new ItemData("Netherlands Antillean Guilder (ANG)", R.drawable.ang));
        list.add(new ItemData("Angolan Kwanza (AOA)", R.drawable.aoa));
        list.add(new ItemData("Argentine Peso (ARS)", R.drawable.ars));
        list.add(new ItemData("Australian Dollar (A$)", R.drawable.aud));
        list.add(new ItemData("Aruban Florin (AWG)", R.drawable.awg));
        list.add(new ItemData("Azerbaijani Manat (AZN)", R.drawable.azn));
        list.add(new ItemData("Bosnia-Herzegovina Convertible Mark (BAM)", R.drawable.bam));
        list.add(new ItemData("Barbadian Dollar (BBD)", R.drawable.bbd));
        list.add(new ItemData("Bangladeshi Taka (BDT)", R.drawable.bdt));
        list.add(new ItemData("Bulgarian Lev (BGN)", R.drawable.bgn));
        list.add(new ItemData("Bahraini Dinar (BHD)", R.drawable.bhd));
        list.add(new ItemData("Burundian Franc (BIF)", R.drawable.bif));
        list.add(new ItemData("Bermudan Dollar (BMD)", R.drawable.bmd));
        list.add(new ItemData("Brunei Dollar (BND)", R.drawable.bnd));
        list.add(new ItemData("Bolivian Boliviano (BOB)", R.drawable.bob));
        list.add(new ItemData("Brazilian Real (R$)", R.drawable.brl));
        list.add(new ItemData("Bahamian Dollar (BSD)", R.drawable.bsd));
        list.add(new ItemData("Bhutanese Ngultrum (BTN)", R.drawable.btn));
        list.add(new ItemData("Botswanan Pula (BWP)", R.drawable.bwp));
        list.add(new ItemData("Belarusian Ruble (BYN)", R.drawable.byn));
        list.add(new ItemData("Belarusian Ruble (2000–2016) (BYR)", R.drawable.byr));
        list.add(new ItemData("Belize Dollar (BZD)", R.drawable.bzd));
        list.add(new ItemData("Canadian Dollar (CA$)", R.drawable.cad));
        list.add(new ItemData("Congolese Franc (CDF)", R.drawable.cdf));
        list.add(new ItemData("Swiss Franc (CHF)", R.drawable.chf));
        list.add(new ItemData("Chilean Unit of Account (UF) (CLF)", R.drawable.clf));
        list.add(new ItemData("Chilean Peso (CLP)", R.drawable.clp));
        list.add(new ItemData("CHN (CHN)", R.drawable.chn));
        list.add(new ItemData("Chinese Yuan (CN¥)", R.drawable.cny));
        list.add(new ItemData("Colombian Peso (COP)", R.drawable.cop));
        list.add(new ItemData("Costa Rican Colón (CRC)", R.drawable.crc));
        list.add(new ItemData("Cuban Peso (CUP)", R.drawable.cup));
        list.add(new ItemData("Cape Verdean Escudo (CVE)", R.drawable.cve));
        list.add(new ItemData("Czech Koruna (CZK)", R.drawable.czk));
        list.add(new ItemData("German Mark (DEM)", R.drawable.dem));
        list.add(new ItemData("Djiboutian Franc (DJF)", R.drawable.djf));
        list.add(new ItemData("Danish Krone (DKK)", R.drawable.dkk));
        list.add(new ItemData("Dominican Peso (DOP)", R.drawable.dop));
        list.add(new ItemData("Algerian Dinar (DZD)", R.drawable.dzd));
        list.add(new ItemData("Egyptian Pound (EGP)", R.drawable.egp));
        list.add(new ItemData("Eritrean Nakfa (ERN)", R.drawable.ern));
        list.add(new ItemData("Ethiopian Birr (ETB)", R.drawable.etb));
        list.add(new ItemData("Euro (€)", R.drawable.eur));
        list.add(new ItemData("Finnish Markka (FIM)", R.drawable.fim));
        list.add(new ItemData("Fijian Dollar (FJD)", R.drawable.fjd));
        list.add(new ItemData("Falkland Islands Pound (FKP)", R.drawable.fkp));
        list.add(new ItemData("French Franc (FRF)", R.drawable.frf));
        list.add(new ItemData("British Pound (£)", R.drawable.gbp));
        list.add(new ItemData("Georgian Lari (GEL)", R.drawable.gel));
        list.add(new ItemData("Ghanaian Cedi (GHS)", R.drawable.ghs));
        list.add(new ItemData("Gibraltar Pound (GIP)", R.drawable.gip));
        list.add(new ItemData("Gambian Dalasi (GMD)", R.drawable.gmd));
        list.add(new ItemData("Guinean Franc (GNF)", R.drawable.gnf));
        list.add(new ItemData("Guatemalan Quetzal (GTQ)", R.drawable.gtq));
        list.add(new ItemData("Guyanaese Dollar (GYD)", R.drawable.gyd));
        list.add(new ItemData("Hong Kong Dollar (HK$)", R.drawable.hkd));
        list.add(new ItemData("Honduran Lempira (HNL)", R.drawable.hnl));
        list.add(new ItemData("Croatian Kuna (HRK)", R.drawable.hrk));
        list.add(new ItemData("Haitian Gourde (HTG)", R.drawable.htg));
        list.add(new ItemData("Hungarian Forint (HUF)", R.drawable.huf));
        list.add(new ItemData("Irish Pound (IEP)", R.drawable.iep));
        list.add(new ItemData("Israeli New Shekel (₪)", R.drawable.ils));
        list.add(new ItemData("Indian Rupee (₹)", R.drawable.inr));
        list.add(new ItemData("Iraqi Dinar (IQD)", R.drawable.iqd));
        list.add(new ItemData("Iranian Rial (IRR)", R.drawable.irr));
        list.add(new ItemData("Icelandic Króna (ISK)", R.drawable.isk));
        list.add(new ItemData("Italian Lira (ITL)", R.drawable.itl));
        list.add(new ItemData("Jamaican Dollar (JMD)", R.drawable.jmd));
        list.add(new ItemData("Jordanian Dinar (JOD)", R.drawable.jod));
        list.add(new ItemData("Kenyan Shilling (KES)", R.drawable.kes));
        list.add(new ItemData("Kyrgystani Som (KGS)", R.drawable.kgs));
        list.add(new ItemData("Cambodian Riel (KHR)", R.drawable.khr));
        list.add(new ItemData("Comorian Franc (KMF)", R.drawable.kmf));
        list.add(new ItemData("North Korean Won (KPW)", R.drawable.kpw));
        list.add(new ItemData("Kuwaiti Dinar (KWD)", R.drawable.kwd));
        list.add(new ItemData("Cayman Islands Dollar (KYD)", R.drawable.kyd));
        list.add(new ItemData("Kazakhstani Tenge (KZT)", R.drawable.kzt));
        list.add(new ItemData("Laotian Kip (LAK)", R.drawable.lak));
        list.add(new ItemData("Lebanese Pound (LBP)", R.drawable.lbp));
        list.add(new ItemData("Sri Lankan Rupee (LKR)", R.drawable.lkr));
        list.add(new ItemData("Liberian Dollar (LRD)", R.drawable.lrd));
        list.add(new ItemData("Lesotho Loti (LSL)", R.drawable.lsl));
        list.add(new ItemData("Lithuanian Litas (LTL)", R.drawable.ltl));
        list.add(new ItemData("Latvian Lats (LVL)", R.drawable.lvl));
        list.add(new ItemData("Libyan Dinar (LYD)", R.drawable.lyd));
        list.add(new ItemData("Moroccan Dirham (MAD)", R.drawable.mad));
        list.add(new ItemData("Moldovan Leu (MDL)", R.drawable.mdl));
        list.add(new ItemData("Malagasy Ariary (MGA)", R.drawable.mga));
        list.add(new ItemData("Macedonian Denar (MKD)", R.drawable.mkd));
        list.add(new ItemData("Mongolian Tugrik (MNT)", R.drawable.mnt));
        list.add(new ItemData("Macanese Pataca (MOP)", R.drawable.mop));
        list.add(new ItemData("Mauritanian Ouguiya (MRO)", R.drawable.mro));
        list.add(new ItemData("Mauritian Rupee (MUR)", R.drawable.mur));
        list.add(new ItemData("Maldivian Rufiyaa (MVR)", R.drawable.mvr));
        list.add(new ItemData("Malawian Kwacha (MWK)", R.drawable.mwk));
        list.add(new ItemData("Mexican Peso (MX$)", R.drawable.mxn));
        list.add(new ItemData("Mozambican Metical (MZN)", R.drawable.mzn));
        list.add(new ItemData("Namibian Dollar (NAD)", R.drawable.nad));
        list.add(new ItemData("Nigerian Naira (NGN)", R.drawable.ngn));
        list.add(new ItemData("Nicaraguan Córdoba (NIO)", R.drawable.nio));
        list.add(new ItemData("Norwegian Krone (NOK)", R.drawable.nok));
        list.add(new ItemData("Nepalese Rupee (NPR)", R.drawable.npr));
        list.add(new ItemData("New Zealand Dollar (NZ$)", R.drawable.nzd));
        list.add(new ItemData("Omani Rial (OMR)", R.drawable.omr));
        list.add(new ItemData("Panamanian Balboa (PAB)", R.drawable.pab));
        list.add(new ItemData("Peruvian Sol (PEN)", R.drawable.pen));
        list.add(new ItemData("Papua New Guinean Kina (PGK)", R.drawable.pgk));
        list.add(new ItemData("Philippine Peso (PHP)", R.drawable.php));
        list.add(new ItemData("Pakistani Rupee (PKR)", R.drawable.pkr));
        list.add(new ItemData("Polish Zloty (PLN)", R.drawable.pln));
        list.add(new ItemData("Paraguayan Guarani (PYG)", R.drawable.pyg));
        list.add(new ItemData("Qatari Rial (QAR)", R.drawable.qar));
        list.add(new ItemData("Romanian Leu (RON)", R.drawable.ron));
        list.add(new ItemData("Serbian Dinar (RSD)", R.drawable.rsd));
        list.add(new ItemData("Russian Ruble (RUB)", R.drawable.rub));
        list.add(new ItemData("Rwandan Franc (RWF)", R.drawable.rwf));
        list.add(new ItemData("Saudi Riyal (SAR)", R.drawable.sar));
        list.add(new ItemData("Solomon Islands Dollar (SBD)", R.drawable.sbd));
        list.add(new ItemData("Seychellois Rupee (SCR)", R.drawable.scr));
        list.add(new ItemData("Sudanese Pound (SDG)", R.drawable.sdg));
        list.add(new ItemData("Swedish Krona (SEK)", R.drawable.sek));
        list.add(new ItemData("St. Helena Pound (SHP)", R.drawable.shp));
        list.add(new ItemData("Slovak Koruna (SKK)", R.drawable.skk));
        list.add(new ItemData("Sierra Leonean Leone (SLL)", R.drawable.sll));
        list.add(new ItemData("Somali Shilling (SOS)", R.drawable.sos));
        list.add(new ItemData("Surinamese Dollar (SRD)", R.drawable.srd));
        list.add(new ItemData("São Tomé & Príncipe Dobra (STD)", R.drawable.std));
        list.add(new ItemData("Salvadoran Colón (SVC)", R.drawable.svc));
        list.add(new ItemData("Syrian Pound (SYP)", R.drawable.syp));
        list.add(new ItemData("Swazi Lilangeni (SZL)", R.drawable.szl));
        list.add(new ItemData("Tajikistani Somoni (TJS)", R.drawable.tjs));
        list.add(new ItemData("Turkmenistani Manat (TMT)", R.drawable.tmt));
        list.add(new ItemData("Tunisian Dinar (TND)", R.drawable.tnd));
        list.add(new ItemData("Tongan Paʻanga (TOP)", R.drawable.top));
        list.add(new ItemData("Turkish Lira (TRY)", R.drawable.tryy));
        list.add(new ItemData("Trinidad & Tobago Dollar (TTD)", R.drawable.ttd));
        list.add(new ItemData("New Taiwan Dollar (NT$)", R.drawable.twd));
        list.add(new ItemData("Tanzanian Shilling (TZS)", R.drawable.tzs));
        list.add(new ItemData("Ukrainian Hryvnia (UAH)", R.drawable.uah));
        list.add(new ItemData("Ugandan Shilling (UGX)", R.drawable.ugx));
        list.add(new ItemData("Uruguayan Peso (UYU)", R.drawable.uyu));
        list.add(new ItemData("Uzbekistani Som (UZS)", R.drawable.uzs));
        list.add(new ItemData("Venezuelan Bolívar (VEF)", R.drawable.vef));
        list.add(new ItemData("Vietnamese Dong (₫)", R.drawable.vnd));
        list.add(new ItemData("Vanuatu Vatu (VUV)", R.drawable.vuv));
        list.add(new ItemData("Samoan Tala (WST)", R.drawable.wst));
        list.add(new ItemData("Central African CFA Franc (FCFA)", R.drawable.xaf));
        list.add(new ItemData("East Caribbean Dollar (EC$)", R.drawable.xcd));
        list.add(new ItemData("Special Drawing Rights (XDR)", R.drawable.xdr));
        list.add(new ItemData("West African CFA Franc (CFA)", R.drawable.cfa));
        list.add(new ItemData("CFP Franc (CFPF)", R.drawable.xpf));
        list.add(new ItemData("Yemeni Rial (YER)", R.drawable.yer));
        list.add(new ItemData("South African Rand (ZAR)", R.drawable.zar));
        list.add(new ItemData("Zambian Kwacha (1968–2012) (ZMK)", R.drawable.zmk));
        list.add(new ItemData("Zambian Kwacha (ZMW)", R.drawable.zmw));
        list.add(new ItemData("Zimbabwean Dollar (2009) (ZWL)", R.drawable.zwl));


        spTo = (Spinner) findViewById(R.id.spinnerTo);
        SpinnerAdapter adapter = new SpinnerAdapter(this,
                R.layout.spinner_item, R.id.tv, list);
        spTo.setAdapter(adapter);
        spTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                to = tTo[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public void checkTheme() {
        share = getSharedPreferences("myLOL", MODE_PRIVATE);
        editor = share.edit();
        DayNight = share.getBoolean("theme", false);
        themeClr = share.getInt("color", 0);
        changeTheme(DayNight, themeClr);
    }

    public void changeTheme(boolean themeMode, int themeColor) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        if (themeMode == false) {
            switch (themeColor) {
                case 0:
                    setTheme(DayBlue);
                    break;
                case 1:
                    setTheme(DayGreen);
                    break;
                case 2:
                    setTheme(DayTeal);
                    break;
                case 3:
                    setTheme(DayIndigo);
                    break;
                case 4:
                    setTheme(DayPink);
                    break;
            }
        } else {
            switch (themeColor) {
                case 0:
                    setTheme(NightBlue);
                    break;
                case 1:
                    setTheme(NightGreen);
                    break;
                case 2:
                    setTheme(NightTeal);
                    break;
                case 3:
                    setTheme(NightIndigo);
                    break;
                case 4:
                    setTheme(NightPink);
                    break;
            }
        }
    }

    public void chageBG(int themeColor) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        switch (themeColor) {
            case 0:
                btnConvert.setBackgroundColor(Color.rgb(33, 150, 243));
                break;
            case 1:
                btnConvert.setBackgroundColor(Color.rgb(76, 175, 80));
                break;
            case 2:
                btnConvert.setBackgroundColor(Color.rgb(0, 150, 136));
                break;
            case 3:
                btnConvert.setBackgroundColor(Color.rgb(63, 81, 181));
                break;
            case 4:
                btnConvert.setBackgroundColor(Color.rgb(233, 30, 99));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image, null);
        builder.setView(view);
        builder.setTitle("Do you want to Exit ?!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAD();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAD();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void loadAD() {
        if (!interstitialAd.isLoaded()) {
            interstitialAd.loadAd(adRequest);
        }
    }

    public void showAD() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            interstitialAd.loadAd(adRequest);
        }
    }

    class showHTML extends  AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String lel = "Error";
            try {
                lel = getHtml(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lel;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            progressDoalog.dismiss();
            result = s;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboardManager.setText(result);
                    Log.d("LOL",result);
                    if (!result.isEmpty() || !result.equals(null)) {
                        tvResult.setText(amount + " " + from + " = " + result +" "+to);
                        ivCp.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(MainActivity.this, "ERROR!! Please try again :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public String getHtml(String url) throws IOException {
            // Build and set timeout values for the request.
            URLConnection connection = (new URL(url)).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // Read and store the result line by line then return the entire string.
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder html = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line);
            }
            in.close();
            String result = html.toString();
            JSONObject jObject;
            try {
                jObject = new JSONObject(result);
                result = jObject.getString("output");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
