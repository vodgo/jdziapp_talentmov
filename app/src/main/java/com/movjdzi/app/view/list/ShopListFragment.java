package com.movjdzi.app.view.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lib.common.util.DataInter;
import com.movjdzi.app.R;
import com.movjdzi.app.adapter.shop.VipBrandSection;
import com.movjdzi.app.adapter.shop.VipBrandSectionViewBinder;
import com.movjdzi.app.model.dto.PayLogDto;
import com.movjdzi.app.model.vo.VipVo;
import com.movjdzi.app.presenter.VipPresenter;
import com.movjdzi.app.presenter.iview.IVipView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author huangyong
 * createTime 2019-09-24
 * 兑换记录
 */
public class ShopListFragment extends Fragment {

    @BindView(R.id.item_list)
    RecyclerView itemList;
    Unbinder unbinder;
    private MultiTypeAdapter adapter;
    private ArrayList<Object> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_coin_vip_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        adapter = new MultiTypeAdapter();
        adapter.register(VipBrandSection.class,new VipBrandSectionViewBinder());
        items = new ArrayList<>();

        items.add(new VipBrandSection(R.drawable.ic_gold_day, "会员日卡", "需要10金币", "有效期一天", new VipBrandSectionViewBinder.OnClickItemPayListener() {
            @Override
            public void onItemClick() {
                payVip(1,"会员日卡");
            }
        }));
        items.add(new VipBrandSection(R.drawable.ic_gold_week, "会员周卡", "需要70金币", "有效期一周", new VipBrandSectionViewBinder.OnClickItemPayListener() {
            @Override
            public void onItemClick() {
                payVip(2, "会员周卡");
            }
        }));
        items.add(new VipBrandSection(R.drawable.ic_gold_month, "会员月卡", "需要300金币", "有效期一月", new VipBrandSectionViewBinder.OnClickItemPayListener() {
            @Override
            public void onItemClick() {
                payVip(3, "会员月卡");
            }
        }));
        items.add(new VipBrandSection(R.drawable.ic_gold_year, "会员年卡", "需要3600金币", "有效期一年", new VipBrandSectionViewBinder.OnClickItemPayListener() {
            @Override
            public void onItemClick() {
                payVip(4, "会员年卡");
            }
        }));

        adapter.setItems(items);
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList.setAdapter(adapter);
    }

    private void payVip(int type, String tip) {


        new XPopup.Builder(getContext()).asConfirm("提示：", "确定兑换"+tip, new OnConfirmListener() {
            @Override
            public void onConfirm() {
                new VipPresenter(iVipView).buyVip(type);
            }
        }).show();



    }
    IVipView iVipView = new IVipView() {
        @Override
        public void payDone(VipVo from) {
            Intent intent = new Intent();
            intent.setAction(DataInter.KEY.ACTION_REFRESH_COIN);
            if (getActivity()!=null){
                getActivity().sendBroadcast(intent);
            }

        }

        @Override
        public void loadLogDone(PayLogDto data) {

        }

        @Override
        public void loadError() {

        }

        @Override
        public void loadEmpty() {

        }
    };
    public static ShopListFragment getInstance() {
        ShopListFragment shopListFragment = new ShopListFragment();
        return shopListFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
