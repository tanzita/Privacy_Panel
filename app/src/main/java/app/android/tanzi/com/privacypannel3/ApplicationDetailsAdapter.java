package app.android.tanzi.com.privacypannel3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Tanzi on 3/20/2016.
 */
public class ApplicationDetailsAdapter extends ArrayAdapter{
    String[] application_permission_text;

    public ApplicationDetailsAdapter(Context context, String[] text) {
        super(context, R.layout.application_details_item, text);

        this.application_permission_text = text;
        this.getContext();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.application_details_item, null, true);
        TextView textView1 = (TextView) row.findViewById(R.id.application_specific_permission_text_view);

        textView1.setText(application_permission_text[position]);

        return row;
    }
}
