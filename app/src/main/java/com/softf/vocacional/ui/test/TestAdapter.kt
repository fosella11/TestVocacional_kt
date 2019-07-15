package com.softf.vocacional.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softf.vocacional.databinding.QuestionsListItemBinding
import com.softf.vocacional.model.Question

class TestAdapter(private val onQuestionsClickedListener: OnQuestionsClickedListener):
    RecyclerView.Adapter<TestAdapter.QuestionsViewHolder>() {


    private val questionList: MutableList<Question> = ArrayList()

    /**
     * inner class
     * Una diferencia importante es que un objeto de la clase embebida está relacionado siempre con un
     * objeto de la clase que la envuelve, de tal forma que las instancias de la clase embebida deben ser
     * creadas por una instancia de la clase que la envuelve. Desde el exterior estas referencias pueden manejarse,
     * pero calificandolas completamente, es decir nombrando la clase externa y luego la interna. Además una
     * instancia de la clase embebida tiene acceso a todos los datos miembros de la
     * clase que la envuelve sin usar ningún calificador de acceso especial (como si le pertenecieran).
     */
    inner class QuestionsViewHolder(private val binding: QuestionsListItemBinding):
        RecyclerView.ViewHolder(binding.root) {


        fun bind(question: Question) {
            binding.apply {
                setQuestion(question)
                onQuestionClickedListener = this@TestAdapter.onQuestionsClickedListener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        return QuestionsViewHolder(
            QuestionsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = questionList.size

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        holder.bind(questionList[position])

    }

    fun clearQuestions() {
        this.questionList.clear()
        notifyDataSetChanged()
    }

    /**
     * Add to list
     */
    fun addQuestions(questionList: List<Question>) {
        this.questionList.addAll(questionList)
        notifyDataSetChanged()
    }

    /**
     * With Filter by ID
     */
    fun addQuestions(questionList: List<Question>, idTest: Int) {
        this.questionList.addAll(questionList)
        this.questionList.filter { it.testId == idTest }
        notifyDataSetChanged()
    }

    interface OnQuestionsClickedListener{
        fun onQuestionClickedListener(question: Question, button: Int)
    }
}

