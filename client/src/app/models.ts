export interface Response {
    code: number
    message: string
    data: string
}

export interface TvSummary {
    id: number
    backdrop: string
    firstAirDate: string
    genreIds: Array<number>
    name: string
    originalLang: string
    originalName: string
    originCountry: Array<string>
    overview: string
    poster: string
    popularity: number
    voteAverage: number
    voteCout: number
}

export interface Tv {
    id: number
    backdrop: string
    cast: Array<CastSummary>
    firstAirDate: string
    genres: Array<string>
    homepage: string
    name: string
    nextEp: Episode
    numOfEps: number
    numOfSeasons: number
    originalLang: string
    originalName: string
    originCountry: Array<string>
    overview: string
    poster: string
    popularity: number
    rating: number
    seasons: Array<SeasonSummary>
    tagline: string
    voteAverage: number
    voteCout: number
}

export interface TVSearchPage {
    page: number
    query: string
    totalResults: number
    totalPages: number
    results: Array<TvSummary>
}

export interface CastSummary {
    id: number
    gender: number
    name: string
    popularity: number
    profile: string
    roles: Array<Map<string, string>>
    totalEpCount: number
}

export interface Episode {
    id: number
    airDate: string
    episodeNum: number
    name: string
    overview: string
    poster: string
    seasonNum: number
    showName: string
}

export interface SeasonSummary {
    id: number
    airDate: string
    episodeCount: number
    name: string
    overview: string
    poster: string
    seasonNum: number
}

export interface User {
    username: string
    password: string
    email: string
    genre: string
    phone: string
}