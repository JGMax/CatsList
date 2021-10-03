package gortea.jgmax.cats.navigation.coordinator

interface Coordinator {
    fun popBackStack()
    fun navigateToFullView(url: String, leftHand: Boolean = true)
}
