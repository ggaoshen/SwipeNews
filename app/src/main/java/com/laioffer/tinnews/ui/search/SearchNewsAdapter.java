package com.laioffer.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SearchNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter
        extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {

    interface ItemCallback {
        void onOpenDetails(Article article);
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();
    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged(); // reload全屏
    }


    // 2. Adapter overrides:
    @NonNull
    @Override
    public SearchNewsAdapter.SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_news_item,
                parent,
                false);
        return new SearchNewsViewHolder(view); //用viewholder的好处是onCreate才需要bind，减少bind次数
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsAdapter.SearchNewsViewHolder holder, int position) {
        // provide data to view
        // 因为是RecyclerView，上滑时#1 view出屏幕，他的ViewHolder被回收，通过此method重新填充数据
        // RecyclerView把填充玩的viewholder放到#8位置上，从屏幕底放入
        Article article = articles.get(position);
        holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        holder.itemTitleTextView.setText(article.title);
        Picasso.get().load(article.urlToImage).into(holder.itemImageView); //不是每次都下载图片，LRU cache加速
        // （url，image binary code的key value store）
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    @Override
    public int getItemCount() { // 告诉有多少data
        return articles.size();
    }

    // 3. SearchNewsViewHolder: 因为binding的速度很慢，所以需要一个viewholder
    // 只在onCreatedViewHolder时才会call binding
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        //为什么需要view holder？减少binding的次数

        ImageView favoriteImageView;
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) { // constructor
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView); // bind xml文件+java view class
            favoriteImageView = binding.searchItemFavorite;
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }

}
