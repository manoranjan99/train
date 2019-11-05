package com.manoranjank.trianinfo;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Manoranjan K on 08-06-2019.
 */

public class myrecyclerlove extends RecyclerView.Adapter<myrecyclerlove.ViewHolder> {

    private List<modelclass1> homelist;
    private Context mContext;



     public myrecyclerlove(Context mcntx,List<modelclass1> homelist )
     {
           this.mContext=mcntx;
           this.homelist=homelist;
     }

    @Override
    public int getItemCount() {
        return homelist.size();
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder //implements View.OnCreateContextMenuListener {
    {
        private CardView mCardView;

        public ViewHolder(CardView itemview) {
            super(itemview);
            mCardView = itemview;

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.attendcard,parent,false);
        return new ViewHolder(cv);


    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

       final modelclass1 hm=homelist.get(position);
        final CardView cardView=holder.mCardView;
        TextView number = (TextView) cardView.findViewById(R.id.number);
        number.setText((hm.getPNR()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(mContext,cardView);
                popup.inflate(R.menu.custom_menu);
                //final dynamicdatamanager homedata;
                final   DatabaseManager mDatabaseMan;

                mDatabaseMan=new DatabaseManager(mContext);
               // homedata=new dynamicdatamanager(mContext);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                final Dialog dialog = new Dialog(mContext);
                               // Button Enter;
                                dialog.setContentView(R.layout.ticketdialog);
                                dialog.setTitle("TICKET");
                                TextView info=(TextView) dialog.findViewById(R.id.userinfoo);

                                info.setText("\n   PNR NO: "+hm.getPNR()+"\n   Name: "+hm.getName()+"\n   Train Name: "+hm.getTrain()+"\n   Seat No: "+hm.getSeatno());

                              /*  Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                                        R.array.planets_array, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
                                TextView text = (TextView) dialog.findViewById(R.id.inputtext);
                                EditText Name = (EditText) dialog.findViewById(R.id.Nameedit);
                                Enter = (Button) dialog.findViewById(R.id.markat);
                                final String nameofperson=Name.getText().toString();

                                Enter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDatabaseMan.insertdata(nameofperson,45,"pallavan","abc234");
                                        dialog.dismiss();
                                    //    homelist.get(position).attend=hm.getAttend()+1;
                                        notifyDataSetChanged();
                                    }
                                });
                             /*   markbun.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        mDatabaseMan.updatedata(hm.getId(), hm.getAttend(), (hm.getBunk()) + 1);
                                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                        homedata.insertstatus(hm.getSubjectname(), currentDateTimeString);
                                        dialog.dismiss();
                                        homelist.get(position).bunk=hm.getBunk()+1;
                                        notifyDataSetChanged();
                                    }
                                });*/
                                dialog.show();
                                break;
                         /*   case R.id.menu2:
                                Intent bunkstatus = new Intent(mContext, bunkstatusm.class);
                                bunkstatus.putExtra(bunkstatusm.EXTRA_POSITION, hm.getSubjectname());
                                mContext.startActivity(bunkstatus);
                                break;
                            case R.id.menu3:
                                //handle menu3 click
                                break;
                            case R.id.menu4:
                                break;*/
                            case R.id.menu2:
                                   homelist.remove(position);
                                   mDatabaseMan.deletedata(hm.getId());
                                   notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }

        });
     }
}



