package com.e444er.cleanmovie.feature_person_detail.presentation.adapter

import com.e444er.cleanmovie.feature_person_detail.domain.model.CastForPerson
import com.e444er.cleanmovie.feature_person_detail.util.PersonMovieBaseAdapter

class PersonCastMovieAdapter() : PersonMovieBaseAdapter<CastForPerson>(){
    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val castForPerson = getItem(position)
        holder.bindCast(cast = castForPerson)
        holder.itemView.setOnClickListener {
            super.clickListener(castForPerson)
        }
    }
}
