package com.e444er.cleanmovie.feature_person_detail.presentation.adapter

import com.e444er.cleanmovie.feature_person_detail.domain.model.CrewForPerson
import com.e444er.cleanmovie.feature_person_detail.util.PersonMovieBaseAdapter

class PersonCrewMovieAdapter : PersonMovieBaseAdapter<CrewForPerson>(){

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val crew = getItem(position)
        holder.bindCrew(crew = crew)
        holder.itemView.setOnClickListener {
            super.clickListener(crew)
        }
    }
}