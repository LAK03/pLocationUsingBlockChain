package kem.anusha.ethereumprodlocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.web3j.abi.datatypes.Int;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Guest1 on 9/13/2017.
 */

public class DisplayAdaptor extends BaseAdapter{




        private final ArrayList mData;
        private TextView tv;



        public DisplayAdaptor(Map<String, String> map) {
            mData = new ArrayList();
            mData.addAll(map.entrySet());
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Map.Entry<String, String> getItem(int position) {
            return (Map.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO implement you own logic with ID
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View result;

            if (convertView == null) {
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_adaptor, parent, false);
            } else {
                result = convertView;
            }

            Map.Entry<String, String> item = getItem(position);

            // TODO replace findViewById by ViewHolder

            ((TextView) result.findViewById(android.R.id.text1)).setText(item.getValue().toString());
           // ((TextView) result.findViewById(android.R.id.text2)).setText("----->" +item.getValue().toString());

            return result;
        }
    }

