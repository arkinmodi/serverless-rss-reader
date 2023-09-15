data "aws_iam_policy_document" "discord_handler_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "discord_handler" {
  name               = var.discord_lambda_function_name
  assume_role_policy = data.aws_iam_policy_document.discord_handler_assume_role.json
}

resource "aws_lambda_function" "discord_handler" {
  function_name = var.discord_lambda_function_name
  filename      = "${path.module}/data/serverless-rss-reader-placeholder-1.0.0.jar"
  role          = aws_iam_role.discord_handler.arn
  handler       = "com.arkinmodi.rssreader.aws.DiscordHandler::handleRequest"
  runtime       = "java17"
  timeout       = 3

  environment {
    variables = {
      AWS_SQS_NAME           = var.serverless_rss_reader_sqs_name
      AWS_SQS_URL            = aws_sqs_queue.serverless_rss_reader.url
      DISCORD_BOT_PUBLIC_KEY = var.discord_bot_public_key
    }
  }
}

resource "aws_lambda_function_url" "discord_handler" {
  function_name      = aws_lambda_function.discord_handler.arn
  authorization_type = "NONE"
}

resource "aws_cloudwatch_log_group" "discord_handler" {
  name              = "/aws/lambda/${var.discord_lambda_function_name}"
  retention_in_days = 14
}

data "aws_iam_policy_document" "discord_handler_lambda_logging" {
  statement {
    actions = [
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents",
    ]
    resources = ["arn:aws:logs:*:*:*"]
  }
}

resource "aws_iam_role_policy" "discord_handler" {
  role   = aws_iam_role.discord_handler.name
  policy = data.aws_iam_policy_document.discord_handler_lambda_logging.json
}

output "serverless_rss_reader_discord_handler_function_url" {
  value = aws_lambda_function_url.discord_handler.function_url
}
