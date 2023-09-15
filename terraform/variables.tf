variable "aws_region" {
  description = "AWS Region"
  default     = "us-east-2"
}

variable "discord_bot_public_key" { type = string }

variable "discord_lambda_function_name" {
  default = "serverless-rss-reader-discord-handler"
}

variable "rss_lambda_function_name" {
  default = "serverless-rss-reader-rss-handler"
}
