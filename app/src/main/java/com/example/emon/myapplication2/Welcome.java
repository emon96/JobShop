package com.example.emon.myapplication2;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rvJoblistt;
    DatabaseReference databaseList;
    private ImageView imageView;
    private LinearLayout layout;
    private CircleImageView profilePic;
    private ImageButton optionBar,findFriend,messanger;

    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    FirebaseAuth mAuth;
    private TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ///////
        /*layout=findViewById(R.id.welcome_layoutId);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,UserProfile.class));
            }
        });*/
        //profilePic=findViewById(R.id.welcome_profile_pic);
        //name=findViewById(R.id.welcome_nameId);
        //imageView1=findViewById(R.id.showimage);
        rvJoblistt=findViewById(R.id.rvJobListId);

        rvJoblistt.setHasFixedSize(true);
        rvJoblistt.setLayoutManager(new LinearLayoutManager(this));
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user==null)
                {
                    startActivity(new Intent(Welcome.this,MainActivity.class));
                }
            }
        };

        mAuth=FirebaseAuth.getInstance();
        databaseList= FirebaseDatabase.getInstance().getReference().child("All Jobs");
        //databaseList.keepSynced(true);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("All Users");
      /*  databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            name.setText(snapshot.getValue().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println( " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilepic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {

                            String propicUri= snapshot.getValue().toString();
                            Picasso.get().load(propicUri).into(profilePic);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println( " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });*/

    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menubar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.postimageId)
        {
            startActivity(new Intent(Welcome.this,ImagePostActivity.class));
        }
        if(item.getItemId()==R.id.menu_logout)
        {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }*/
    private void logOut()
    {

        mAuth.signOut();
        finish();
        //startActivity(new Intent(Welcome.this,MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<UserImageList,UploadImageHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<UserImageList, UploadImageHolder>(
                UserImageList.class,
                R.layout.userimagelist,
                UploadImageHolder.class,
                databaseList

        ) {
            @Override
            protected void populateViewHolder(UploadImageHolder viewHolder, final UserImageList model, int position) {
                //final String user_key=getRef(position).getKey();


                viewHolder.setTitle(model.getTitle());
                viewHolder.setCompany(model.getCompany());
                viewHolder.setQuality(model.getQuality());
                viewHolder.setExperience(model.getExperience());
                viewHolder.setDeadline(model.getDeadline());

          /*      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!model.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        {
                            Intent friend_intent=new Intent(Welcome.this,Friend_Profile_Activity.class);
                            friend_intent.putExtra(getString(R.string.friend_add_key),model.getId());
                            startActivity(friend_intent);
                        }
                        else
                        {
                            startActivity(new Intent(Welcome.this,UserProfile.class));
                        }

                    }
                });*/




            }
        };
        rvJoblistt.setAdapter(firebaseRecyclerAdapter);


        ///////

    }

    //////

    public static   class UploadImageHolder extends RecyclerView.ViewHolder{
        View view;
        private DatabaseReference ref;

        public UploadImageHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }
        public void setTitle(String title)
        {
            TextView image_title=view.findViewById(R.id.titleId);
            image_title.setText(title);

        }
        public void  setCompany(String company)
        {
            TextView com=view.findViewById(R.id.companyId);
            com.setText(company);
        }



        public void setQuality(String quality) {
            TextView req=view.findViewById(R.id.requirementId);
            req.setText(quality);
        }
        public void setExperience(String experience) {
            TextView exp=view.findViewById(R.id.experiencetId);
            exp.setText(experience);
        }
        public void setDeadline(String deadline) {
            TextView dead=view.findViewById(R.id.deadlineId);
            dead.setText(deadline);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.jobcategoryId) {
            // Handle the camera action
            startActivity(new Intent(Welcome.this,OptionPage.class));
        } else if (id == R.id.postjobId) {
            startActivity(new Intent(Welcome.this,ImagePostActivity.class));

        } else if (id == R.id.postedjobsId) {

        } else if (id == R.id.interestedjobsId) {

        } else if (id == R.id.profileId) {
            startActivity(new Intent(Welcome.this,UserProfile.class));

        } else if (id == R.id.settingsId) {
            startActivity(new Intent(Welcome.this, OptionPage.class));

        }
        else if(id==R.id.logoutId)
        {
            mAuth.signOut();
            finish();
        }
        else if(id==R.id.createAccountId)
        {

        }
        else if(id==R.id.signinId)
        {
            startActivity(new Intent(Welcome.this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
