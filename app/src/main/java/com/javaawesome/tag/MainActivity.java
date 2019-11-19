package com.javaawesome.tag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.amazonaws.amplify.generated.graphql.CreatePlayerMutation;
import com.amazonaws.amplify.generated.graphql.CreateSessionMutation;
import com.amazonaws.amplify.generated.graphql.ListSessionsQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

import type.CreatePlayerInput;
import type.CreateSessionInput;

public class MainActivity extends AppCompatActivity implements SessionAdapter.OnSessionInteractionListener {

    private final String TAG = "javatag";
    RecyclerView recyclerNearbySessions;
    SessionAdapter sessionAdapter;
    List<ListSessionsQuery.Item> sessions;
    AWSAppSyncClient awsAppSyncClient;
    String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize aws mobile client and check if you are logged in or not
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                // if the user is signed out, show them the sign in page
                if (result.getUserState().toString().equals("SIGNED_OUT")) {
                    signInUser();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });

        // connect to AWS
        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        sessions = new LinkedList<>();

        recyclerNearbySessions = findViewById(R.id.recycler_nearby_sessions);
        recyclerNearbySessions.setLayoutManager(new LinearLayoutManager(this));
        this.sessionAdapter = new SessionAdapter(this.sessions, this);
        recyclerNearbySessions.setAdapter(this.sessionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryAllSessions();
    }

    public void goToMap(View view) {
        EditText sessionName = findViewById(R.id.editText_session_name);
        CreateSessionInput input = CreateSessionInput.builder()
                .title(sessionName.getText().toString())
                .lat(47.608013) // geocoder things :slightly_smiling_face:
                .lon(-122.335167)
                .radius(50)
                .build();
        CreateSessionMutation createSessionMutation = CreateSessionMutation.builder().input(input).build();
        awsAppSyncClient.mutate(createSessionMutation).enqueue(new GraphQLCall.Callback<CreateSessionMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreateSessionMutation.Data> response) {
                sessionId = response.data().createSession().id();
                CreatePlayerInput playerInput = CreatePlayerInput.builder()
                        .playerSessionId(response.data().createSession().id())
                        .isIt(false)
                        .lat(47.608013)
                        .lon(-122.335167)
                        .username(AWSMobileClient.getInstance().getUsername())
                        .build();
                CreatePlayerMutation createPlayerMutation = CreatePlayerMutation.builder().input(playerInput).build();
                awsAppSyncClient.mutate(createPlayerMutation).enqueue((new GraphQLCall.Callback<CreatePlayerMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<CreatePlayerMutation.Data> response) {
                        Log.i(TAG, "player mutation happened! ... inside of a session mutation");
                        Intent goToMapIntent = new Intent(MainActivity.this, MapsActivity.class);
                        goToMapIntent.putExtra("sessionId", sessionId);
                        MainActivity.this.startActivity(goToMapIntent);
                    }
                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.i(TAG, "mutation of player failed, boohoo!");
                    }
                }));
            }
            @Override
            public void onFailure(@Nonnull ApolloException e) {
            }
        });

    }

    // Direct users to sign in page
    public void signInUser() {
        AWSMobileClient.getInstance().showSignIn(MainActivity.this,
                // customize the built in sign in page
                SignInUIOptions.builder().backgroundColor(16763080).build(),
                new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails result) {
                        Log.i(TAG, "successfully show signed in page");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }

    // sign out user and show them sign in page
    public void signoutCurrentUser(View view) {
        AWSMobileClient.getInstance().signOut();
        signInUser();
    }

    //
    @Override
    public void joinExistingGameSession(ListSessionsQuery.Item session) {
        Intent goToMapIntent = new Intent(this, MapsActivity.class);
        goToMapIntent.putExtra("sessionId", session.id());
        this.startActivity(goToMapIntent);
    }

    // get all sessions
    public void queryAllSessions() {
        awsAppSyncClient.query(ListSessionsQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.NETWORK_ONLY)
                .enqueue(getAllSessionsCallBack);
    }

    public GraphQLCall.Callback<ListSessionsQuery.Data> getAllSessionsCallBack = new GraphQLCall.Callback<ListSessionsQuery.Data>() {
        @Override
        public void onResponse(@Nonnull final Response<ListSessionsQuery.Data> response) {
            Log.i(TAG, "got sessions data back from dynamodb");
            Handler h = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message inputMessage) {
                    sessions.addAll(response.data().listSessions().items());
                    Log.i(TAG, sessions.toString());
                    sessionAdapter.notifyDataSetChanged();
                }
            };
            h.obtainMessage().sendToTarget();
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {

        }
    };
}
