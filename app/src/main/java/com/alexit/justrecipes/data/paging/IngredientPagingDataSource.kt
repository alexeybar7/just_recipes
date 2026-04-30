package com.alexit.justrecipes.data.paging

/*
class IngredientPagingDataSource(
    private val recipesRepository: RecipesRepository
): PagingSource<Int, IngredientModel>() {

    override fun getRefreshKey(state: PagingState<Int, IngredientModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IngredientModel> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = recipesRepository.getIngredients(nextPageNumber)
            return LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (nextPageNumber == response.isE.info.pages) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
        }
    }
}

 */