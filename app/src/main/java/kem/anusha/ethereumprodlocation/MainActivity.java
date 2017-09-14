package kem.anusha.ethereumprodlocation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.NumericType;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.infura.InfuraHttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import kem.anusha.ethereumprodlocation.productLocation;
import kem.anusha.ethereumprodlocation.R;

import static kem.anusha.ethereumprodlocation.R.id.pLoc;
import static kem.anusha.ethereumprodlocation.R.layout.activity_main;
import static org.spongycastle.asn1.ua.DSTU4145NamedCurves.params;
import static org.spongycastle.asn1.x500.style.RFC4519Style.c;


public class MainActivity extends AppCompatActivity {

    private final static String privateKeyRopsten = "2af85196032c500fca39750ac14c3f4d02daa47576a7bbe3ea12fbe52b49e2b3"; // Add your Private key
    private final static String productLocationContractAddressRopsten = "0x2fb799879597dc05d8443b52e2b138a8419e4e6b"; // Deployed contract address
    private final static String privateNWUrl = "http://192.168.0.104:8545"; // Link where contract is running

   // private ProgressBar progressBar;
    private EditText _rfID;
    private EditText _pName;
    private EditText _pLoc;
    private TextView gasPriceTextView;
    private TextView gasLimitTextView;
    private SeekBar gasPriceSeekBar;
    private EditText _edittext_rfId;
    private GridView _list;
    private TextView tv;



    List<String> locations = new ArrayList<String>();
    ArrayList<String> addr = new ArrayList<>();


    private Web3j web3j;

    private Credentials credentials = Credentials.create(privateKeyRopsten);
    private int minimumGasLimit = 2000000;
    private BigInteger gasLimit = new BigInteger(String.valueOf(minimumGasLimit));

    String productRfId;
    String noOfProdReg;
    String rfIdCnt;
    String result="";
    String productName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        initUi();
        setGasPriceText(10);
        Log.d("establishing ","connection");
        setGasLimit(minimumGasLimit);
        initWeb3j();

    }

    private void initUi() {

       // progressBar = (ProgressBar) findViewById(R.id.progressbar);
        _rfID = (EditText) findViewById(R.id.pRfId);
        _pName = (EditText) findViewById(R.id.pName);
        _pLoc = (EditText) findViewById(pLoc);
        _edittext_rfId = (EditText) findViewById(R.id.rfId);
        _list = (GridView) findViewById(R.id.list);
        tv = (TextView) findViewById(R.id.title);
        Button readButton = (Button) findViewById(R.id.button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productRfId = _edittext_rfId.getText().toString();
                Log.d("User rfID",productRfId);
                getLocation(productRfId);
            }
        });
        Button writeButton = (Button) findViewById(R.id.write_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeLocationToContract();
            }
        });
        gasPriceSeekBar = (SeekBar) findViewById(R.id.gas_price_seek_bar);

        gasPriceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setGasPriceText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar gasLimitSeekBar = (SeekBar) findViewById(R.id.gas_limit_seek_bar);
        gasLimitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setGasLimit(progress + minimumGasLimit);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        gasLimitTextView = (TextView) findViewById(R.id.gas_limit_text_view);
        gasPriceTextView = (TextView) findViewById(R.id.gas_price_text_view);
    }

    private void initWeb3j() {
        InitWeb3JTask task = new InitWeb3JTask();
        task.execute(privateNWUrl);
    }

    public void setGasPriceText(int gasPrice) {
        String formattedString = getString(R.string.gas_price, String.valueOf(gasPrice));
        gasPriceTextView.setText(formattedString);
    }

    private BigInteger getGasPrice() {
        int gasPriceGwei = gasPriceSeekBar.getProgress();
        BigInteger gasPriceWei = BigInteger.valueOf(gasPriceGwei + 1000000000L);
        Log.d("wat", "getGasPrice: " + String.valueOf(gasPriceGwei));
        return gasPriceWei;
    }

    public void setGasLimit(int gasLimit) {
        String gl = String.valueOf(gasLimit);
        this.gasLimit = new BigInteger(gl);
        gasLimitTextView.setText(getString(R.string.gas_limit, gl));
    }

    private void writeLocationToContract() {
       // progressBar.setVisibility(View.VISIBLE);
        WriteTask writeTask = new WriteTask();
        writeTask.execute(_rfID.getText().toString(),_pName.getText().toString(),_pLoc.getText().toString());
    }

    private void getLocation(String productRfId) {
        try {
           // progressBar.setVisibility(View.VISIBLE);
            ReadTask readTask = new ReadTask();
            readTask.execute(productRfId);

        } catch (Exception e) {
            Log.d("Exception", "in retriving the Location = " + e.getMessage());
        }
    }


     class ReadTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            Utf8String rfID = new Utf8String(params[0]);
            try {

                productLocation productloc = productLocation.load(productLocationContractAddressRopsten, web3j, credentials, getGasPrice(), gasLimit);
                Future<Int> proCnt = productloc.prodCnt();
                Int cnt = proCnt.get();
                noOfProdReg =  (cnt.getValue()).toString();

                Future<Int> prorfIdCnt = productloc.getrfIdCount(rfID);
                Int rfIdcnt = prorfIdCnt.get();
                rfIdCnt =  (rfIdcnt.getValue()).toString();

            } catch (Exception e) {
                result = "Error reading the smart contract";
                e.printStackTrace();
            }
            return rfIdCnt;
        }

        @Override
        protected void onPostExecute(String rfIdCnt) {
            super.onPostExecute(rfIdCnt);
          //  progressBar.setVisibility(View.INVISIBLE);
            callGetDetails(rfIdCnt);
        }
    }

    public void callGetDetails(String rfIdCnt)
    {
            GetDetails getdetails = new GetDetails();
            getdetails.execute(productRfId);


    }
     class GetDetails extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Utf8String rfID = new Utf8String(params[0]);
            int cnt = Integer.parseInt(rfIdCnt);
            locations.clear();

            try {
                productLocation productloc = productLocation.load(productLocationContractAddressRopsten, web3j, credentials, getGasPrice(), gasLimit);
                for (int in = 0;in<cnt; in++) {
                    BigInteger i = new BigInteger(String.valueOf(in));
                    Uint u = new Uint(i);

                    Future<Utf8String> locInfo = productloc.getProductDetails(rfID, u);
                    Utf8String greetingUtf8 = locInfo.get();
                    result = greetingUtf8.getValue();
                    locations.add(result);
                }

                Future<Utf8String> locInfo = productloc.getProductName(rfID);
                Utf8String greetingUtf8 = locInfo.get();
                productName = greetingUtf8.getValue();
                Log.d("product Name",productName);

                for (String s : locations){
                    Log.d("My array list content: ", s);
                }

            } catch (Exception e) {
                result = "Error during transaction. Error: " ;
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           // progressBar.setVisibility(View.INVISIBLE);
            Log.d("OnpostExecution func",result);

            calculateLocOccurances();



        }
    }


    public void  calculateLocOccurances()
    {

        double latitude=0,longitude=0;

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        final Map<String, String> map = new HashMap<String, String>();
        for (String s : locations){

            StringTokenizer str = new StringTokenizer(s,",");
            latitude = Double.parseDouble((str.nextToken()));
            longitude = Double.parseDouble((str.nextToken()));
            Log.d("latitude",String.valueOf(latitude));
            Log.d("longitude",String.valueOf(longitude));
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String place = "";
                Log.d("Known Name",knownName);
                if (address!= null && !(address.contains("Unnamed")))
                    place = place+ address +"\n";
                if(city!= null && !(city.contains("Unnamed")))
                    place = place+city +"\n";
                if(state!=null && !(state.contains("Unnamed")))
                    place = place + state +"\n";
                if(country!=null && !(country.contains("Unnamed")))
                    place = place + country+"\n";
                if(postalCode!=null && !(postalCode.contains("Unnamed")))
                    place = place + postalCode;
                map.put(s,place);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Map.Entry<String, String> e : map.entrySet()) {
            System.out.println(e.getKey() + "---" + e.getValue());

        }

        tv.setText(" Locations for product "+productName);
        DisplayAdaptor adaptor = new DisplayAdaptor(map);
        _list.setAdapter(adaptor);
        _list.setClickable(true);
        _list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String location = parent.getItemAtPosition(position).toString();
               // String LocLatLog = map.get(location);
                StringTokenizer str = new StringTokenizer(location,"=");
               // String add = str.nextToken();
                String pLoc = str.nextToken();

                //String qty = str.nextToken();
                Log.d("location ",location);
                Log.d("pLoc",pLoc);

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("location",pLoc);
                intent.putExtra("productName",productName);
               // intent.putExtra("Quantity",qty);

                startActivity(intent);



            }

        });


    }
    private class WriteTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Utf8String rfID =new Utf8String(params[0]);
            Utf8String pName = new Utf8String(params[1]);
            Utf8String pLocation =new  Utf8String(params[2]);

            String result;
            try {
                productLocation productloc = productLocation.load(productLocationContractAddressRopsten, web3j, credentials, getGasPrice(), gasLimit);
                TransactionReceipt transactionReceipt = productloc.addProdInfo(rfID,pName,pLocation).get(3, TimeUnit.MINUTES);
                result = "Successful added location information to Ethereum Blockchain ";

            } catch (Exception e) {
                result = "Error during transaction. Error: " + e.getMessage();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
         //   progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    private class InitWeb3JTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpService  httpService;
            String result = "Success initializing web3j";
            try {
                httpService = new HttpService(privateNWUrl);
                web3j = Web3jFactory.build(httpService);
            } catch (Exception e) {
                String exception = e.toString();
                Log.d("ERROR", "Error initializing web3j Error: " + exception);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
