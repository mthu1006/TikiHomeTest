package vn.thudlm.hometestjava.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.thudlm.hometestjava.R;
import vn.thudlm.hometestjava.adapters.DealAdapter;
import vn.thudlm.hometestjava.adapters.KeyWordAdapter;
import vn.thudlm.hometestjava.models.Deal;
import vn.thudlm.hometestjava.models.KeyWord;
import vn.thudlm.hometestjava.utils.AppContants;
import vn.thudlm.hometestjava.utils.AppDialogManager;
import vn.thudlm.hometestjava.utils.HttpUrlConnectionUtil;
import vn.thudlm.hometestjava.utils.Validation;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private ArrayList<KeyWord> listKeyWords;
    private ArrayList<Deal> listDeals;
    private RecyclerView rvKeyWords, rvDeals;
    private KeyWordAdapter keyWordAdapter;
    private DealAdapter dealAdapter;

    private Dialog loadingDialog;

    private final int STATE_LOAD_KEYWORDS = 1;
    private final int STATE_LOAD_DEALS = STATE_LOAD_KEYWORDS + 1;
    private final int STATE_LOAD_FINISH = STATE_LOAD_DEALS + 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rvKeyWords = root.findViewById(R.id.rv_keywords);
        rvDeals = root.findViewById(R.id.rv_deals);
        initControls();
        changeState(STATE_LOAD_KEYWORDS);
        return root;
    }

    private void changeState(int state){
        switch (state){
            case STATE_LOAD_KEYWORDS:
                loadingDialog.show();
                initKeywords();
                break;
            case STATE_LOAD_DEALS:
                initDeals();
                break;
            case STATE_LOAD_FINISH:
                loadingDialog.dismiss();
                break;
        }
    }

    private void initControls() {
        loadingDialog = AppDialogManager.createLoadingDialog(getActivity());

        listKeyWords = new ArrayList<>();
        keyWordAdapter = new KeyWordAdapter(getActivity(), listKeyWords);
        rvKeyWords.setAdapter(keyWordAdapter);
        rvKeyWords.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));

        listDeals = new ArrayList<>();
        rvDeals.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        dealAdapter = new DealAdapter(getActivity(), listDeals);
        rvDeals.setAdapter(dealAdapter);
    }

    //region Keywords
    private void initKeywords(){
        HttpUrlConnectionUtil.with(getActivity()).onFinish(new HttpUrlConnectionUtil.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                changeState(STATE_LOAD_DEALS);
                if(!Validation.checkNullOrEmpty(output)){
                    getKeywordsFromJSON(output);
                }
            }
        }).execute(AppContants.URL_KEYWORDS);
    }

    private void getKeywordsFromJSON(String str){
        JSONArray arr = null;
        try {
            arr = new JSONArray(str);
            for (int i = 0; i < arr.length(); i++) {
                listKeyWords.add(new KeyWord(arr.get(i).toString()));
            }
            keyWordAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Deals
    private void initDeals(){
        HttpUrlConnectionUtil.with(getActivity()).onFinish(new HttpUrlConnectionUtil.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                changeState(STATE_LOAD_FINISH);
                if(!Validation.checkNullOrEmpty(output)){
                    getDealsFromJSON(output);
                }
            }
        }).execute(AppContants.URL_DEAL);
    }

    private void getDealsFromJSON(String str){
        try {
            JSONObject object = new JSONObject(str);
            if(object.getInt(AppContants.KEY_RETURN_CODE) == AppContants.RETURN_CODE_SUCCESS) {
                JSONArray arr = object.getJSONObject(AppContants.KEY_RESULT).getJSONArray(AppContants.KEY_PRODUCT);
                for (int i = 0; i < arr.length(); i++) {
                    listDeals.add(Deal.parseJSON(arr.getJSONObject(i)));
                }
                dealAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion

}
